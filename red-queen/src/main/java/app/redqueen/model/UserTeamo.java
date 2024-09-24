package app.redqueen.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "user_teamo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserTeamo implements Cloneable
{
    @Id
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "last_visit")
    private Date lastVisit;

    @Column(name = "city")
    private String city;

    @Column(name = "zodiac")
    private String zodiac;

    @Column(name="height")
    private Integer height;

    @Column(name= "gender")
    private String gender;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_teamo_has_t_like",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "t_like_id")
    )
    private List<Like> likeList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER , cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_teamo_has_t_dislikes",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "t_dislikes_id")
    )
    private List<Dislike> dislikeList= new ArrayList<>(); ;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_teamo_has_general_attribute",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "general_attribute_id")
    )
    private List<GeneralAttribute> generalAttributes = new ArrayList<>();;

    @OneToMany(mappedBy = "userSender",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MessageTeamo> messagesFromThisUser= new ArrayList<>();;

    @OneToMany(mappedBy = "userReceiver",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MessageTeamo> messagesToThisUser;

    @OneToMany(mappedBy = "user",cascade = CascadeType.MERGE)
    private List<Photo> photos= new ArrayList<>();;

    @Column(name="password")
    private String password;

    @Column(name="token")
    private String token;

    @Column(name="email")
    private String email;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private UserTeamoBlock block;

    @Column(name="sys_create_date")
    private Date sysCreateDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Lifestyle> lifestyleList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<UserLooking> userLookings = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "user_has_user_blacklist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_in_blacklist_id")
    )
    private List<UserTeamo> blackList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "user_has_user_blacklist",
            joinColumns = @JoinColumn(name = "user_in_blacklist_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserTeamo> blackListsOwners = new ArrayList<>();

    @Builder.Default
    @Column(name="create_source")
    private String createSource = "Untraceable create source";

    @Override
    public UserTeamo clone()
    {
        try
        {
            UserTeamo clone = (UserTeamo) super.clone();

            clone.setLikeList(this.getLikeList());
            clone.setDislikeList(this.getDislikeList());
            clone.setGeneralAttributes(this.getGeneralAttributes());
            clone.setPhotos(this.getPhotos());

            return clone;

        } catch (CloneNotSupportedException ex)
        {
            throw new AssertionError();
        }
    }

    @Override
    public String toString()
    {
        return String.format("id=%s,name=%s",
                this.getId(),
                this.getName()
        );
    }
}
