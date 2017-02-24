package authenticationserver.ao;

import authenticationserver.entities.ServerUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Created by igor on 24.02.17.
 */
public class UserAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAO.class);

    @Autowired
    private UserRepository userRepository;


    public static String hashPassword(String password, byte[] salt)
    {
        try {
            int iterations = 1000;
            char[] chars = password.toCharArray();

            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return Base64.getEncoder().encodeToString(skf.generateSecret(spec).getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error("Exception occurred during pass generation: ", e);
            throw new RuntimeException(e);
        }
    }


    private static byte[] getSalt()
    {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt;
        } catch(NoSuchAlgorithmException e) {
            LOGGER.error("Exception occurred during salt generation: ", e);
            throw new RuntimeException(e);
        }
    }


    public void create(String login, String email, String pass)
    {
        byte[] salt = getSalt();
        if (userRepository.findByLogin(login) != null)
        {
            throw new UserExistsException();
        }
        userRepository.save(new ServerUser(login, email, salt, hashPassword(pass, salt)));
    }


    public ServerUser verify(String login, String pass)
    {
        ServerUser serverUser = userRepository.findByLogin(login);

        return serverUser == null || !serverUser.getPassHash().equals(hashPassword(pass, serverUser.getPassSalt())) ? null : serverUser;
    }


    public ServerUser getById(long id)
    {
        return userRepository.findById(id);
    }


    public ServerUser getByLogin(String login)
    {
        return userRepository.findByLogin(login);
    }


    public void changeStatus(long id, ServerUser.Statuses status)
    {
        ServerUser serverUser = userRepository.findById(id);
        if (serverUser == null)
        {
            throw new RuntimeException("ServerUser does not exists");
        }

        if (serverUser.getStatus() == status) return;

        serverUser.setStatus(status);
        userRepository.save(serverUser);
    }

}
