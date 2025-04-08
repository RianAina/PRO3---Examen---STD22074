package school.hei.patrimoine.cas.example;

import static java.time.Month.*;
import static school.hei.patrimoine.modele.Argent.ariary;
import static school.hei.patrimoine.modele.Devise.MGA;

import java.time.LocalDate;
import java.util.Set;
import school.hei.patrimoine.cas.Cas;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Compte;
import school.hei.patrimoine.modele.possession.Correction;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;

public class Tiana extends Cas {

    private final Compte financeur;

    public Tiana() {
        super(LocalDate.of(2024, APRIL, 8), LocalDate.MAX, new Personne("Bako"));
        financeur = new Compte("Banque", LocalDate.MIN, ariary(60_000_000));
    }

    @Override
    protected String nom() {
        return "Bako";
    }

    @Override
    protected Devise devise() {
        return MGA;
    }

    @Override
    protected void init() {
        new FluxArgent(
                "Banque", financeur, LocalDate.of(2024, APRIL, 2), ariary(2_125_000));
    }

    @Override
    public Set<Possession> possessions() {
        var au8avril24 = LocalDate.of(2024, APRIL, 8);
        var trainDeVie =
                new FluxArgent(
                        "Vie courante et projet",
                        financeur,
                        au8avril24.minusDays(100),
                        au8avril24.plusDays(100),
                        5,
                        ariary(-9_000_000));
        var terrain = new Materiel("Terrain bati", au8avril24.minusDays(3), au8avril24, ariary( 100_000_000), 10);
        return Set.of(financeur, trainDeVie, terrain);
    }

    @Override
    protected void suivi() {
        new Correction(new FluxArgent("Correction à la hausse", financeur, ajd, ariary(0)));
    }
}
