<template>
  <div class="container">
    <div class="glass-effect">
      <div class="heure">
        <div class="hda">{{ formatHeureDepart }} - {{ formatHeureArrivee }}</div>
        <div class="duree">{{ tempsEnMinutes() }} min</div>
      </div>
      <div class="trajet">
        <div v-for="(ligne, index) in lignes" :key="index" class="lignes">
          <img src="../components/icons/arrow.png" alt="Arrow Image" class="fleche" />
          <img :src="getLineImage(ligne)" :alt="ligne" class="ligne-image" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import { stations } from "@/stockage/trajets";

const lineMap = {
  "Ligne 1": 'src/components/lines/metro-1.png',
  "Ligne 2": 'src/components/lines/metro-2.png',
  "Ligne 3": 'src/components/lines/metro-3.png',
  "Ligne 4": 'src/components/lines/metro-4.png',
  "Ligne 5": 'src/components/lines/metro-5.png',
  "Ligne 6": 'src/components/lines/metro-6.png',
  "Ligne 7": 'src/components/lines/metro-7.png',
  "Ligne 8": 'src/components/lines/metro-8.png',
  "Ligne 9": 'src/components/lines/metro-9.png',
  "Ligne 10": 'src/components/lines/metro-10.png',
  "Ligne 11": 'src/components/lines/metro-11.png',
  "Ligne 12": 'src/components/lines/metro-12.png',
  "Ligne 13": 'src/components/lines/metro-13.png',
  "Ligne 14": 'src/components/lines/metro-14.png',
  "Ligne A": 'src/components/lines/rer-a.png',
  "Ligne B": 'src/components/lines/rer-b.png',
  "Ligne C": 'src/components/lines/rer-c.png',
  "Ligne D": 'src/components/lines/rer-d.png',
  "Ligne E": 'src/components/lines/rer-e.png',
  "Logo metro": 'src/components/lines/logo-metro.png',
  "Logo RER": 'src/components/lines/logo-RER.png'
};

export default {
  setup() {
    const tempsTotal = ref(0);
    const lignes = ref([]);
    const formatHeureDepart = ref('');
    const formatHeureArrivee = ref('');

    // Fonction pour calculer le temps total en minutes
    const tempsEnMinutes = () => {
      let total = 0;
      stations.forEach(station => {
        if (station.chemins) {
          station.chemins.forEach(chemin => {
            chemin.forEach(segment => {
              total += segment.temps;
            });
          });
        }
      });
      return Math.round(total / 60);
    };

    // Fonction pour récupérer les noms des lignes
    const recupererNomsLignes = () => {
      stations.forEach(station => {
        if (station.chemins) {
          station.chemins.forEach(chemin => {
            chemin.forEach(segment => {
              Object.keys(segment).forEach(key => {
                if (key !== 'temps') {
                  lignes.value.push(key);
                }
              });
            });
          });
        }
      });
    };

    // Appeler la fonction pour récupérer les noms des lignes
    recupererNomsLignes();

    // Mettre à jour l'heure de départ
    formatHeureDepart.value = (() => {
      const now = new Date();
      const hours = now.getHours().toString().padStart(2, '0');
      const minutes = now.getMinutes().toString().padStart(2, '0');
      return `${hours}:${minutes}`;
    })();

    // Mettre à jour l'heure d'arrivée
    formatHeureArrivee.value = (() => {
      const now = new Date();
      const timeInMinutes = tempsEnMinutes();
      now.setMinutes(now.getMinutes() + timeInMinutes);
      const hours = now.getHours().toString().padStart(2, '0');
      const minutes = now.getMinutes().toString().padStart(2, '0');
      return `${hours}:${minutes}`;
    })();

    // Fonction pour obtenir le chemin de l'image de la ligne
    function getLineImage(ligne) {
      return lineMap[ligne] || '';
    }

    return {
      tempsEnMinutes,
      lignes,
      getLineImage,
      formatHeureDepart,
      formatHeureArrivee
    };
  }
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 15px;
  z-index: 1;
  height: fit-content;
  margin: 0;
  justify-content: center;
  background: linear-gradient(135deg, rgba(248, 96, 96, 0.5), rgba(218, 3, 3, 0.8));
}

.glass-effect {
  border-radius: 15px;
  padding: 2rem;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(2px);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1), 0 6px 20px rgba(0, 0, 0, 0.1);
  border: 2px solid rgba(255, 255, 255, 1);
  align-items: center;
  color: #fff;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
}

.trajet {
  width: fit-content;
  height: 10vh;
  border-width: 2px;
  background-color: rgba(234, 165, 165, 0.62);
  margin-bottom: 1rem;
  margin-top: 1rem;
  padding-right: 1rem;
  border-color: black;
  border-radius: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

.ligne-image {
  max-width: 100%;
  max-height: 100%;
}

.nom-ligne {
  font-weight: bold;
  margin-right: 0.5rem; /* Espacement entre le nom de la ligne et l'image */
}

img {
  width: 1.5rem;
  height: 1.5rem;
}

.fleche {
  filter: invert(1);
  transform: rotate(-90deg);
  width: 1rem;
  height: 1rem;
  margin-left: 1rem;
  margin-right: 1rem;
}

.heure {
  font-weight: bold;
  padding: 0.4rem;
  display: flex; /* Utilisation de Flexbox pour le conteneur */
  justify-content: space-between;
}

.lignes {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
