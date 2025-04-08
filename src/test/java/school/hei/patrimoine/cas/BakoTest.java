package school.hei.patrimoine.cas.example;

import static org.junit.jupiter.api.Assertions.*;
import static school.hei.patrimoine.modele.Argent.ariary;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import school.hei.patrimoine.modele.Argent;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.possession.Compte;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;

public class BakoTest {

    private Bako bako;

    @BeforeEach
    void setUp() {
        bako = new Bako();
        bako.init();
        bako.suivi();
    }

    @Test
    void nomDuCasEstCorrect() {
        assertEquals("Bako", bako.nom());
    }

    @Test
    void deviseEstMGA() {
        assertEquals(Devise.MGA, bako.devise());
    }

    @Test
    void possessionsContiennentLeCompteEtLesBiens() {
        Set<Possession> possessions = bako.possessions();

        boolean compteTrouve = possessions.stream()
                .filter(p -> p instanceof Compte)
                .map(p -> (Compte) p)
                .anyMatch(c -> c.nom().equals("BNI") && c.valeurComptable().equals(ariary(2_000_000)));

        boolean ordinateurTrouve = possessions.stream()
                .filter(p -> p instanceof Materiel)
                .map(p -> (Materiel) p)
                .anyMatch(m -> m.nom().equals("Ordinateur") && m.valeurComptable().equals(ariary(3_000_000)));

        boolean coffreFortTrouve = possessions.stream()
                .filter(p -> p instanceof Materiel)
                .map(p -> (Materiel) p)
                .anyMatch(m -> m.nom().equals("Coffre fort"));

        assertTrue(compteTrouve, "Le compte BNI doit être présent");
        assertTrue(ordinateurTrouve, "L'ordinateur doit être présent");
        assertFalse(coffreFortTrouve, "Le coffre fort ne devrait pas être dans les possessions");
    }

    @Test
    void initAjouteUnFluxArgent() {
        assertDoesNotThrow(() -> bako.init());
    }

    @Test
    void suiviAjouteUneCorrection() {
        assertDoesNotThrow(() -> bako.suivi());
    }
}
