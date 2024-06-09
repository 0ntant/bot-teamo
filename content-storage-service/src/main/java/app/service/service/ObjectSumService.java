package app.service.service;

import app.service.repository.ObjectSumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjectSumService
{
    @Autowired
    ObjectSumRepository objectSumRep;
}
