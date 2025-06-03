package com.example.pdprobiblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ksiazka {

  private int id;
  private String tytul;
  private String gatunek;
  private String dataWydania;
  private String dodano;
  private Integer idautora;
  private Integer idplacowki;
  private Boolean rezerwacja;
  private Boolean czywyporzyczono;


  @Override
  public String toString() {
    return "Ksiazka{id=" + id + ", Tytul='" + tytul + '\'' + ", Gatunek='" + gatunek + '\''
        + ", Data_Wydania=" + dataWydania + ", Dodano=" + dodano + ", id_autora=" + idautora
        + ", rezerwacja=" + rezerwacja + ", id_placowki=" + idplacowki + ", czywyporzyczono="
        + czywyporzyczono + "}";
  }
}
