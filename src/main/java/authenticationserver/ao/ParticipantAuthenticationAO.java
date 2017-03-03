package authenticationserver.ao;

import authenticationserver.entities.ParticipantAuthentication;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by igor on 26.02.17.
 */
public class ParticipantAuthenticationAO {

    @Autowired
    private ParticipantAuthenticationRepository pARepository;


    /**
     * Проверяет, была ли произведена успешная аутентификация с указанным значением.
     *
     * @return true - если была, false - если не было
     */
    public boolean checkAuthentication(long value)
    {
        return pARepository.findByValueAndErrorIsNull(value) != null;
    }


    public void logAuthenticationAttempt(long participantId, long value, String error, long authenticatorId)
    {
        pARepository.save(new ParticipantAuthentication(participantId, value, error, authenticatorId));
    }

}
