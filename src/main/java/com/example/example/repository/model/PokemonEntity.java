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
@Table(name = PokemonEntity.TABLE_NAME)
public class PokemonEntity implements Serializable {
    public static final String TABLE_NAME = "pokemons";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "height")
    private String height;

    @Column(name = "img_url")
    private String imgUrl;

    @ManyToMany(mappedBy = "pokemonList", fetch = FetchType.LAZY)
    private List<UserEntity> users;
}
