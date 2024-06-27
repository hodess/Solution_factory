<template>
  <div class="container">
    <div class="glass-effect">
      <template v-for="(station, stationIndex) in stations" :key="stationIndex">
        <div class="chemin"
             :class="{ 'selected': stationIndex === selectedStationIndex }"
             @click="selectStation(stationIndex)">
          <div class="heure">
            <div class="hda">{{ getHeureDepart(station) }} - {{ getHeureArrivee(station) }}</div>
            <div class="duree">{{ tempsEnMinutes(station) }} min</div>
          </div>
          <div class="trajet">
            <template v-for="(ligne, index) in getLignes(station)" :key="index">
              <div class="lignes">
                <img :src="getLineImage(ligne)" :alt="ligne" class="ligne-image" />
                <img v-if="index < getLignes(station).length - 1" src="./icons/arrow.png" alt="Arrow Image" class="fleche" />
              </div>
            </template>
          </div>
        </div>
        <div  class="divider"></div>
      </template>
    </div>
  </div>
</template>



<script>
import { ref, onMounted } from "vue";
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
    const selectedStationIndex = ref(null); // State to track selected station index


    // Function to select a station by its index
    const selectStation = (index) => {
      selectedStationIndex.value = index;
    };

    // Fonction pour calculer le temps total en minutes
    /*const tempsEnMinutes = () => {
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
    };*/
      // Fonction pour calculer le temps total en minutes pour un chemin spécifique
      const tempsEnMinutes = (station) => {
        let total = 0;
        if (station && station.chemins) {
          station.chemins.forEach(chemin => {
            chemin.forEach(segment => {
              total += segment.temps;
            });
          });
        }
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

    // Fonction pour obtenir l'heure de départ pour un chemin spécifique
    const getHeureDepart = () => {
      const now = new Date();
      const hours = now.getHours().toString().padStart(2, '0');
      const minutes = now.getMinutes().toString().padStart(2, '0');
      return `${hours}:${minutes}`;
    };

    // Fonction pour obtenir l'heure d'arrivée pour un chemin spécifique
    const getHeureArrivee = (station) => {
      const now = new Date();
      const timeInMinutes = tempsEnMinutes(station);
      now.setMinutes(now.getMinutes() + timeInMinutes);
      const hours = now.getHours().toString().padStart(2, '0');
      const minutes = now.getMinutes().toString().padStart(2, '0');
      return `${hours}:${minutes}`;
    };
    // Fonction pour récupérer les noms des lignes pour un chemin spécifique
    const getLignes = (station) => {
      const lignes = [];
      if (station && station.chemins) {
        station.chemins.forEach(chemin => {
          chemin.forEach(segment => {
            Object.keys(segment).forEach(key => {
              if (key !== 'temps') {
                lignes.push(key);
              }
            });
          });
        });
      }
      return lignes;
    };

    // On component mount, select the first station by default
    onMounted(() => {
      selectStation(0);
    });

    return {
      stations,
      selectedStationIndex,
      tempsEnMinutes,
      getLignes,
      getLineImage,
      getHeureDepart,
      getHeureArrivee,
      selectStation
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
  background: linear-gradient(135deg, rgba(248, 96, 96, 0.6), rgba(218, 3, 3, 0.9));
}

.glass-effect {
  border-radius: 15px;
  padding: 1rem;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(2px);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1), 0 6px 20px rgba(0, 0, 0, 0.1);
  border: 2px solid rgba(255, 255, 255, 1);
  align-items: center;
  color: #fff;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
}

.trajet {
  padding: 1rem;
  width: fit-content;
  min-width: 15vw;
  height: fit-content;
  border-width: 2px;
  background-color: rgb(252, 193, 193, 0.6);
  margin-bottom: 0.4rem;
  margin-top: 0.4rem;
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
  width: 1rem;
  height: 1rem;
  margin-left: 1rem;
  margin-right: 1rem;
}

.heure {
  font-weight: bold;
  display: flex; /* Utilisation de Flexbox pour le conteneur */
  justify-content: space-between;
}

.lignes {
  display: flex;
  align-items: center;
  justify-content: center;
}

.divider{
  width: 100%; /* Largeur de la barre */
  height: 2px; /* Hauteur de la barre */
  background-color: white; /* Couleur de la barre */
  margin-bottom: 1rem;
}

.selected{

}

.chemin {
  padding: 0;
  border-width: 0 0 0 0;
  transition: padding 0.3s ease-in-out, transform 0.3s ease-in-out;
}

.chemin.selected {
  padding: 0.5rem;
  background-color: rgb(0, 0, 0, 0.05);
  border-radius: 8px 8px 0 0;
  border-width: 2px 2px 0 2px;
  border-style: solid;
  border-color: white;
  transition: padding 0.3s ease-in-out, transform 0.3s ease-in-out;
}
</style>
