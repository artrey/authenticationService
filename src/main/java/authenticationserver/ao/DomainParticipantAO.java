package authenticationserver.ao;

import authenticationserver.entities.DomainParticipant;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by igor on 26.02.17.
 */
public class DomainParticipantAO {

    /** Время, в течение которого действует сгенерированный код пользователя. Т.е. после кодирования своего текущего
     * времени пользователь должен предъявить этот код не позже чем через этот период времени */
    @Value("${timeGapSec:180}")
    private long timeGapSec;

    @Autowired
    private DomainParticipantRepository dPRepository;

    @Autowired
    private ParticipantAuthenticationAO participantAuthenticationAO;


    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getLong();
    }


    public ParticipantIdAndPrivateKey createDomainParticipant(long domainId, String name, long creatorUserId)
    {
        if (name == null)
        {
            // Имя должно быть уникальным или null
            return null;
        }

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(512);
            KeyPair keypair = keyGen.genKeyPair();
            PrivateKey privateKey = keypair.getPrivate();
            PublicKey publicKey = keypair.getPublic();

            DomainParticipant dp = dPRepository.save(new DomainParticipant(domainId, name,
                    Base64.encodeBase64String(publicKey.getEncoded()), creatorUserId));

            return new ParticipantIdAndPrivateKey(dp.getId(), Base64.encodeBase64String(privateKey.getEncoded()));
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }


    /**
     * Осуществляет аутентификацию пользователя по коду.
     *
     * @returns null, если аутентификация прошла успешно, в противном случае - описание ошибки
     */
    public String authenticate(long pId, String encodedTimeBase64, long authenticatorId)
    {
        DomainParticipant participant = dPRepository.findById(pId);
        if (participant == null)
        {
            throw new RuntimeException(String.format("Participant with id %d does not exists", pId));
        }

        String error = null;
        long decTime = 0;
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");

            PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(participant.getPublicKey())));
            Cipher cipherDec = Cipher.getInstance("RSA");
            cipherDec.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] dec = cipherDec.doFinal(Base64.decodeBase64(encodedTimeBase64));
            decTime = bytesToLong(dec);

            if (Math.abs(System.currentTimeMillis()/1000 - decTime) > timeGapSec)
            {
                error = "InvalidCode:"+decTime;
            }
            else if (participantAuthenticationAO.checkAuthentication(decTime))
            {
                // Тут может быть проблема - из-за некорректно выставленных часов пользователь может сгенерировать код,
                // который станет валиден когда-нибудь в будущем. Если кто-то в это время его перехватит, то сможет
                // пройти в это время. Но, думаю, без разницы
                error = "SecondAttempt:"+decTime;
            }

        } catch(InvalidKeyException | InvalidKeySpecException | IllegalBlockSizeException | BadPaddingException e) {
            error = "DecodeError";
        }catch(NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

        participantAuthenticationAO.logAuthenticationAttempt(pId, decTime, error, authenticatorId);
        return error;
    }


    public DomainParticipant getById(long pId) {
        return dPRepository.findById(pId);
    }


    public void changeStatus(long pId, DomainParticipant.Statuses status)
    {
        DomainParticipant participant = dPRepository.findById(pId);

        if (participant == null)
        {
            throw new RuntimeException(String.format("Participant with id %d does not exists", pId));
        }

        if (participant.getStatus() == status) return;

        participant.setStatus(status);
        dPRepository.save(participant);
    }


    /*public static void main(String[] args) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(512);
        KeyPair keypair = keyGen.genKeyPair();
        PrivateKey privateKey = keypair.getPrivate();
        PublicKey publicKey = keypair.getPublic();

        String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());
        String privateKeyString =  Base64.encodeBase64String(privateKey.getEncoded());
        //String publicKeyString = new String(publicKey.getEncoded());
        //String privateKeyString =  new String(privateKey.getEncoded());

        System.out.println(publicKey.getEncoded().length+" "+privateKey.getEncoded().length);

        System.out.println("PUBLIC: "+ new String(publicKey.getEncoded()));
        System.out.println("PRIVATE: "+new String(privateKey.getEncoded()));

        System.out.println("PUBLIC: "+publicKeyString.length()+" "+publicKeyString);
        System.out.println("PRIVATE: "+privateKeyString.length()+" "+privateKeyString);

        long time = System.currentTimeMillis();

        KeyFactory kf = KeyFactory.getInstance("RSA");

        privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString)));
        publicKey = kf.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString)));



        Cipher cipherEnc = Cipher.getInstance("RSA");
        cipherEnc.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] enc = cipherEnc.doFinal(longToBytes(time));

        System.out.println("ENCR: "+enc.length);

        Cipher cipherDec = Cipher.getInstance("RSA");
        cipherEnc.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] dec = cipherEnc.doFinal(enc);
        long decTime = bytesToLong(dec);

        System.out.println("Times: "+time+" "+decTime);
    }*/


    public static class ParticipantIdAndPrivateKey
    {
        private final long pId;
        private final String privateKey;

        public ParticipantIdAndPrivateKey(long pId, String privateKey) {
            this.pId = pId;
            this.privateKey = privateKey;
        }

        public long getpId() {
            return pId;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public String toString()
        {
            return pId+"|"+privateKey;
        }
    }

}
