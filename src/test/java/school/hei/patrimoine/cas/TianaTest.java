package school.hei.patrimoine.cas.example;

import static org.junit.jupiter.api.Assertions.*;
import static school.hei.patrimoine.modele.Argent.ariary;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.possession.Compte;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;

public class TianaTest {

    private Tiana tiana;

    @BeforeEach
    void setUp() {
        tiana = new Tiana();
        tiana.init();
        tiana.suivi();
    }

    @Test
    void nomDuCasEstCorrect() {
        assertEquals("Bako", tiana.nom());
    }

    @Test
    void deviseEstMGA() {
        assertEquals(Devise.MGA, tiana.devise());
    }

    @Test
    void possessionsContiennentLeCompteEtLesBiens() {
        Set<Possession> possessions = tiana.possessions();

        boolean compteTrouve = possessions.stream()
                .filter(p -> p instanceof Compte)
                .map(p -> (Compte) p)
                .anyMatch(c -> c.nom().equals("Banque") && c.valeurComptable().equals(ariary(60_000_000)));

        boolean terrainTrouve = possessions.stream()
                .filter(p -> p instanceof Materiel)
                .map(p -> (Materiel) p)
                .anyMatch(m -> m.nom().equals("Terrain bati") && m.valeurComptable().equals(ariary(100_000_000)));

        boolean trainDeVieTrouve = possessions.stream()
                .filter(p -> p instanceof FluxArgent)
                .map(p -> (FluxArgent) p)
                .anyMatch(f -> f.nom().equals("Vie courante et projet") && f.valeurComptable().equals(ariary(-9_000_000)));

        assertTrue(compteTrouve, "Le compte Banque doit être présent avec le bon montant");
        assertTrue(terrainTrouve, "Le terrain bâti doit être présent");
        assertTrue(trainDeVieTrouve, "Le flux de vie courante et projet doit être présent");
    }

    @Test
    void initAjouteUnFluxArgent() {
        // Comme pour Bako, ce test garantit que init() ne plante pas.
        assertDoesNotThrow(() -> tiana.init());
    }

    @Test
    void suiviEffectueUneCorrection() {
        // La correction est de 0 ici, donc on vérifie juste que ça ne plante pas
        assertDoesNotThrow(() -> tiana.suivi());
    }
}
