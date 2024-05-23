package app.redqueen.service.database;

import app.redqueen.model.MessageTeamo;
import app.redqueen.model.UserTeamo;
import app.redqueen.repository.MessageTeamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MessageTeamoService
{
    @Autowired
    MessageTeamoRepository messageRep;

    public List<MessageTeamo> findChat(UserTeamo botUser, UserTeamo userTeamo )
    {

        return  messageRep.findChat(botUser,userTeamo);
    }

    public MessageTeamo findByBodyAndCreateDate(String body, Date createDate )
    {
        return messageRep
                .findByBodyAndCreateDate(body, createDate)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("No message with body %s and create date %s",
                                body,
                                createDate)
                ));
    }

    public boolean isExistMessageWithBodyAndCreateDate(String body, Date createDate)
    {
        return messageRep
                .findByBodyAndCreateDate(body, createDate)
                .isPresent();
    }

    public List<MessageTeamo> getAllMessageByUser(UserTeamo userReceiverSender)
    {
        return messageRep.findAllMessagesByUser(userReceiverSender);
    }

    public MessageTeamo findLastMessageByUser(UserTeamo userReceiverSender)
    {
        return messageRep
                .findLastMessageByUser(userReceiverSender)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                String.format("No such message with user id %S"
                                        ,userReceiverSender.getId()
                                )
                        )
                );
    }

    public List<MessageTeamo> getAll()
    {
        return messageRep.findAll();
    }
}
