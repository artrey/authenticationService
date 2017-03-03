package authenticationserver.ao;

import authenticationserver.entities.WhiteOrganization;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by igor on 28.02.17.
 */
public interface WhiteOrganizationsRepository extends Repository<WhiteOrganization, Long> {

    WhiteOrganization save(WhiteOrganization whiteOrganization);

    WhiteOrganization findByUIdAndOIdAndRemTimeIsNull(long uId, long oId);

    List<WhiteOrganization> findByUIdAndRemTimeIsNull(long uId);

}
