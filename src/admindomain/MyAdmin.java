package admindomain;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sangzhenya on 2016/3/28.
 */
@Entity
@Table(name = "myadmin")
public class MyAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    private String email;

    public MyAdmin() {
    }

    public MyAdmin(String name, String password, String email) {

        this.name = name;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
