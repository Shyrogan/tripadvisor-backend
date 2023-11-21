package fr.samyseb.common.pojo;

public record CarteBancaire(
        String numero,
        int mois,
        int annee,
        String cryptogramme
) {
}
