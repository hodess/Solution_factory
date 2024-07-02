<script>
import { ref, onMounted } from "vue";
import axios from "axios";
import { chemin_json } from '@/config';
import MetroMap from './MetroMap.vue';
import eventBus from '../../eventBus.js';




const lineMap = {
  'ligne_1': 'src/components/lines/metro-1.png',
  'ligne_2': 'src/components/lines/metro-2.png',
  'ligne_3': 'src/components/lines/metro-3.png',
  'ligne_3B': 'src/components/lines/metro-3-bis.png',
  'ligne_4': 'src/components/lines/metro-4.png',
  'ligne_5': 'src/components/lines/metro-5.png',
  'ligne_6': 'src/components/lines/metro-6.png',
  'ligne_7': 'src/components/lines/metro-7.png',
  'ligne_7B': 'src/components/lines/metro-7-bis.png',
  'ligne_8': 'src/components/lines/metro-8.png',
  'ligne_9': 'src/components/lines/metro-9.png',
  'ligne_10': 'src/components/lines/metro-10.png',
  'ligne_11': 'src/components/lines/metro-11.png',
  'ligne_12': 'src/components/lines/metro-12.png',
  'ligne_13': 'src/components/lines/metro-13.png',
  'ligne_14': 'src/components/lines/metro-14.png',
  "Logo metro": 'src/components/lines/logo-metro.png',
  "Logo RER": 'src/components/lines/logo-RER.png',
  'ligne_A': 'src/components/lines/rer-a.png',
  'ligne_B': 'src/components/lines/rer-b.png',
  'ligne_C': 'src/components/lines/rer-c.png',
  'ligne_D': 'src/components/lines/rer-d.png',
  'ligne_E': 'src/components/lines/rer-e.png',
};


export default {

  methods: {
    callPrinfeur() {
      // Utilisation de $parent pour accéder au composant MetroMap
      MetroMap.methods.prinfeur.call(this)
    },


    emettreEvenement(numChemin) {
      // console.log(`Événement cheminClique émis avec numéro : ${numChemin}`);
      eventBus.emit('cheminClique', numChemin);

    },
  },

  setup() {
    // const tempsTotal = ref(0);
    // const lignes = ref([]);
    const formatHeureDepart = ref('');
    const formatHeureArrivee = ref('');
    const selectedStationIndex = ref(null); // State to track selected station index
    chemin_json.value = ref({ chemins: [] }); // Enfaite ct chat qui avait changé en mettant [] dedans
    const cheminJsonValue = ref(chemin_json.value);



    const updateCheminJson = () => {
      chemin_json.value = cheminJsonValue.value;
    };
    const emettreEvenement = (numChemin) => {
      // console.log(`Événement cheminClique émis avec numéro : ${numChemin}`);
      eventBus.emit('cheminClique', numChemin);
    }


    setTimeout(function () {
      emettreEvenement(1); // Appel sans argument car par défaut, numChemin est 1
    }, 1500);

    const selectStation = (index) => {
      selectedStationIndex.value = index;
      // console.log(index)
    };

    const getLignes = (chemin) => {
      if (!chemin) {
        return [];
      }
      return Object.keys(chemin).filter(key => key !== 'temps' && key !== 'distance');
    };

    function fetchAndLogResult() {
      let start = localStorage.getItem('currentDepart');
      let end = localStorage.getItem('currentArrivee');
      axios.get(`http://localhost:8081/find_gare?start=${start}&end=${end}`)
        .then(response => {
          chemin_json.value = response.data;  // Update the reference
          //console.log(chemin_json.value.chemins);
          // console.log("distance :"+chemin_json.value.chemins[0].distance);

        })
        .catch(error => {
          //  console. error('Error fetching data:', error);
        });
    }

    // Fonction pour calculer le temps total en minutes pour un chemin spécifique
    const tempsEnMinutes = (chemin) => {
      if (!chemin || !chemin.temps) {
        return 0; // Return 0 if chemin or temps does not exist
      }
      return Math.round(chemin.temps / 60);
    };

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
      ligne = "ligne_" + ligne.trim();
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

    const getHeureArrivee = (chemin) => {
      const now = new Date();
      const timeInMinutes = tempsEnMinutes(chemin);
      now.setMinutes(now.getMinutes() + timeInMinutes);
      const hours = now.getHours().toString().padStart(2, '0');
      const minutes = now.getMinutes().toString().padStart(2, '0');
      return `${hours}:${minutes}`;
    };

    const ShowEco = (chemin) => {
      const metroEco = 3.8;
      const voitureEco = 134;
      const logoEcolo = 'src/assets/simple-leaf-logo-free-png.png';
      const logoVoiture = 'src/assets/symbole-de-voiture-icone-png-vert.png';
      const metroCO2 = (chemin.distance * metroEco).toFixed(1);
      const voitureCO2 = (chemin.distance * voitureEco).toFixed(1);
      return `
    <span >
      <img src="${logoEcolo}"  class = "logoEco" alt="Eco logo" style="width: 25px; height: 25px;" /> ${metroCO2} gCO2 
      <span class="eco-separator"></span>
      <img src="${logoVoiture}" class ="logoVoiture" alt="Car logo" style="width: 20px; height: 20px;" /> ${voitureCO2} gCO2 
    </span>
  `; 
};

// On component mount, select the first station by default
    onMounted(() => {
      // console.log('Le composant Paths est monté');
      selectStation(0);
      fetchAndLogResult();
    });


    return {
      chemin_json,
      cheminJsonValue,
      updateCheminJson,
      selectedStationIndex,
      tempsEnMinutes,
      getLignes,
      getLineImage,
      getHeureDepart,
      getHeureArrivee,
      selectStation,
      fetchAndLogResult,
      formatHeureDepart,
      formatHeureArrivee,
      ShowEco,


    };
  }
};
</script>

<template>
  <div class="container">
    <div class="glass-effect" v-if="chemin_json.chemins">
      <template v-for="(chemin, cheminIndex) in chemin_json.chemins" :key="cheminIndex">
        <div class="chemin" :class="{ 'selected': cheminIndex === selectedStationIndex }"
          @click="emettreEvenement(cheminIndex + 1); selectStation(cheminIndex);">
          <div class="heure">
            <div class="hda">{{ getHeureDepart() }} - {{ getHeureArrivee(chemin) }}</div>
            <div class="duree">{{ tempsEnMinutes(chemin) }} min</div>
          </div>
          <div class="trajet">
            <template v-for="(ligne, index) in getLignes(chemin)" :key="index">
              <div class="lignes">
                <img :src="getLineImage(ligne)" :alt="ligne" class="ligne-image" />
                <img v-if="index < getLignes(chemin).length - 1" src="./icons/arrow.png" alt="Arrow Image" class="fleche" />
              </div>
            </template>
          </div>
          <!-- <button @click="callPrinfeur">Lancer prinfeur</button> -->
          <div class="eco" v-html="ShowEco(chemin)"></div>
        </div>
        <div class="divider"></div>
      </template>
      <!-- <button class="print-button" @click="PrintThePage"><span class="print-icon"></span></button> -->
    </div>
  </div>
</template>


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
  margin-right: 0.5rem;
  /* Espacement entre le nom de la ligne et l'image */
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
  display: flex;
  /* Utilisation de Flexbox pour le conteneur */
  justify-content: space-between;
}

.lignes {
  display: flex;
  align-items: center;
  justify-content: center;
}

.divider {
  width: 100%;
  /* Largeur de la barre */
  height: 2px;
  /* Hauteur de la barre */
  background-color: white;
  /* Couleur de la barre */
  margin-bottom: 1rem;
}

.selected {}

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

button.print-button {
  width: 100px;
  height: 100px;
}

span.print-icon,
span.print-icon::before,
span.print-icon::after,
button.print-button:hover .print-icon::after {
  border: solid 4px #333;
}

span.print-icon::after {
  border-width: 2px;
}

button.print-button {
  position: relative;
  padding: 0;
  border: 0;

  border: none;
  background: transparent;
}

span.print-icon,
span.print-icon::before,
span.print-icon::after,
button.print-button:hover .print-icon::after {
  box-sizing: border-box;
  background-color: #fff;
}

span.print-icon {
  position: relative;
  display: inline-block;
  padding: 0;
  margin-top: 20%;

  width: 60%;
  height: 35%;
  background: #fff;
  border-radius: 20% 20% 0 0;
}

span.print-icon::before {
  content: "";
  position: absolute;
  bottom: 100%;
  left: 12%;
  right: 12%;
  height: 110%;

  transition: height .2s .15s;
}

span.print-icon::after {
  content: "";
  position: absolute;
  top: 55%;
  left: 12%;
  right: 12%;
  height: 0%;
  background: #fff;
  background-repeat: no-repeat;
  background-size: 70% 90%;
  background-position: center;
  background-image: linear-gradient(to top,
      #fff 0, #fff 14%,
      #333 14%, #333 28%,
      #fff 28%, #fff 42%,
      #333 42%, #333 56%,
      #fff 56%, #fff 70%,
      #333 70%, #333 84%,
      #fff 84%, #fff 100%);

  transition: height .2s, border-width 0s .2s, width 0s .2s;
}

button.print-button:hover {
  cursor: pointer;
}

button.print-button:hover .print-icon::before {
  height: 0px;
  transition: height .2s;
}

button.print-button:hover .print-icon::after {
  height: 120%;
  transition: height .2s .15s, border-width 0s .16s;
}

.eco{
  font-size: 1rem;
  color: rgb(25, 143, 14);
  margin-top: 0.5rem;
}

span.eco-separator {
  width: 1rem; /* Adjust this value to simulate a tab space */
}
</style>
