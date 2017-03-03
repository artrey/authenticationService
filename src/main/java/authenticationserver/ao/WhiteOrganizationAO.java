package authenticationserver.ao;

import authenticationserver.entities.Organization;
import authenticationserver.entities.WhiteOrganization;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by igor on 28.02.17.
 */
public class WhiteOrganizationAO {

    @Autowired
    private OrganizationAO organizationAO;

    @Autowired
    private WhiteOrganizationsRepository wORepository;


    public List<Organization> getWhiteOrganizations(long uId)
    {
        return wORepository.findByUIdAndRemTimeIsNull(uId).stream().map(wo -> organizationAO.getById(wo.getoId()))
                .collect(Collectors.toList());
    }


    public void addToWhileList(long uId, long oId)
    {
        WhiteOrganization wo = wORepository.findByUIdAndOIdAndRemTimeIsNull(uId, oId);
        if (wo != null) return;

        wORepository.save(new WhiteOrganization(uId, oId));
    }


    public void remFromWhiteList(long uId, long oId)
    {
        WhiteOrganization wo = wORepository.findByUIdAndOIdAndRemTimeIsNull(uId, oId);
        if (wo == null) return;
        wo.setRemTime(new Date());
        wORepository.save(wo);
    }


    public boolean isWhiteOrganization(long uId, long oId)
    {
        return wORepository.findByUIdAndOIdAndRemTimeIsNull(uId, oId) != null;
    }

}
