package ru.belyaeva.springapp1.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name = "cats")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(max = 15, message = "Имя может быть в пределах от 2 до 15 букв")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Возраст не должен быть пустым")
    @Column(name = "age")
    private String age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "image")
    private String image;


    public Cat(Long id, String name, String age, String gender, String image) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.image = image;
    }

    public Cat(){}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
