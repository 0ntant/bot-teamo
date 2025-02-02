package app.service.service;

import app.service.model.ImageAvatar;
import app.service.model.ObjectSum;
import app.service.repository.ObjectSumRepository;
import app.service.util.HashUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class ObjectSumService
{
    @Autowired
    ObjectSumRepository objectSumRep;

    public void regObject(byte[] data)
    {
        save(data);
    }

    public boolean isObjectReg(byte[] objectData)
    {
        return isObjectReg(createChecksum(objectData));
    }

    private boolean isObjectReg(String objectHash)
    {
        return objectSumRep
                .findByObjectHash(objectHash)
                .isPresent();
    }

    public void save(byte[] data)
    {
        ObjectSum objectSum = new ObjectSum();
        objectSum.setObjectHash(createChecksum(data));
        objectSumRep.save(objectSum);
    }

    public void save(byte[] data, ImageAvatar imageAvatar)
    {
        ObjectSum objectSum = new ObjectSum();
        objectSum.setObjectHash(createChecksum(data));
        objectSum.setImageAvatar(imageAvatar);
        objectSumRep.save(objectSum);
    }

    private String createChecksum(byte[] data)
    {
        return HashUtil.sha256(data);
    }
}
