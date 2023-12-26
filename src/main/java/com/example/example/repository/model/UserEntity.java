package com.example.example.repository.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = UserEntity.TABLE_NAME)
public class UserEntity implements Serializable {
    public static final String TABLE_NAME = "users";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_pokemon",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "pokemon_id")}
    )
    private List<PokemonEntity> pokemonList;
}
