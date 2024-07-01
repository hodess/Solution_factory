<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

import DepartDestination from "@/components/DepartDestination.vue";
import HeureDepartArrivee from "@/components/HeureDepartArrivee.vue";

const router = useRouter();
const dropdownButton = ref(null);
const handleClick = () => {
  toggleHeureDepartArrivee();
  rotateButton();
};
let messageReçu = ref("");
let probleme = ref(false);
function handleMessageForCookies(message) {
  messageReçu.value = message;
}

const getCurrentTime = () => {
  const now = new Date();
  const hour = String(now.getHours()).padStart(2, '0');
  const minute = String(now.getMinutes()).padStart(2, '0');
  return `${hour}:${minute}`;
};

const currentTime = ref(getCurrentTime());
sessionStorage.setItem('currentTime', currentTime.value);
sessionStorage.setItem('type_Time', 0);

const showHeureDepartArrivee = ref(false);
const showDepartDestination = ref(true); // Nouvelle variable réactive

const toggleHeureDepartArrivee = () => {
  showHeureDepartArrivee.value = !showHeureDepartArrivee.value;
};

const rotateButton = () => {
  if (dropdownButton.value) {
    // Définir la transition CSS
    dropdownButton.value.style.transition = 'transform 0.3s, background-color 0.3s';

    if (!dropdownButton.value.classList.contains('rotated')) {
      // Si le bouton n'est pas déjà tourné, le tourner
      dropdownButton.value.style.transform = 'rotate(180deg)';
      dropdownButton.value.classList.add('rotated');
    } else {
      // Sinon, le remettre à sa position d'origine
      dropdownButton.value.style.transform = '';
      dropdownButton.value.classList.remove('rotated');
    }
  }
};
// function cleanArray(arr) {
//   return arr.filter(element => element !== undefined && element !== "");
// }
const navigateToMap = () => {
<<<<<<< HEAD
  if (messageReçu.value.depart == messageReçu.value.arrivee) {
    probleme.value = true;
    return;
  }
  else {
    console.log(messageReçu.value);
    let départFromStorage = localStorage.getItem("départ")
    let ArriveeFromStorage = localStorage.getItem("arrivée")
    if (départFromStorage == null || ArriveeFromStorage == null) {
      localStorage.setItem('départ', messageReçu.value.depart);
      localStorage.setItem('arrivée', messageReçu.value.arrivee);
    }
    else {
      let tab1 = départFromStorage.split(";")
      let tab2 = ArriveeFromStorage.split(";")
      let cleanedParts = tab1.filter(part => part && part.trim() !== 'undefined');
      départFromStorage = cleanedParts.join(';')
      cleanedParts = tab2.filter(part => part && part.trim() !== 'undefined');
      ArriveeFromStorage = cleanedParts.join(';')
      départFromStorage += ";" + messageReçu.value.depart
      ArriveeFromStorage += ";" + messageReçu.value.arrivee
      localStorage.setItem('départ', départFromStorage);
      localStorage.setItem('arrivée', ArriveeFromStorage);
    }
    localStorage.setItem('currentDepart', messageReçu.value.depart);
    localStorage.setItem('currentArrivee', messageReçu.value.arrivee);
    router.push('/map');
    if (window.location.pathname === '/map') {
      window.location.reload();
    }
=======
  console.log(messageReçu.value);
  let départFromStorage = localStorage.getItem("départ");
  let ArriveeFromStorage = localStorage.getItem("arrivée");
  if (départFromStorage == null || ArriveeFromStorage == null) {
    localStorage.setItem('départ', messageReçu.value.depart);
    localStorage.setItem('arrivée', messageReçu.value.arrivee);
  } else {
    départFromStorage += ";" + messageReçu.value.depart;
    ArriveeFromStorage += ";" + messageReçu.value.arrivee;
    localStorage.setItem('départ', départFromStorage);
    localStorage.setItem('arrivée', ArriveeFromStorage);
  }
  localStorage.setItem('currentDepart', messageReçu.value.depart);
  localStorage.setItem('currentArrivee', messageReçu.value.arrivee);
  showDepartDestination.value = false; // Cacher DepartDestination
  router.push('/map');
  if (window.location.pathname === '/map') {
    window.location.reload();
>>>>>>> ecolo
  }
};


</script>


<template>
  <div class="container">
    <div class="glass-effect">
      <div class="title-trip-wrapper">
        <div class="title-trip">Où voulez-vous aller ?</div>
      </div>
      <DepartDestination v-if="showDepartDestination" ref="departDestination" @update-stations="handleMessageForCookies" />
      <div class=" dropdown-button-container">
        <div class="dropdown-button-container-sticky">
          <div class="dropdown-button-text">Quand :</div>
          <button ref="dropdownButton" @click="handleClick" class="dropdown-button"></button>
        </div>
      </div>
      <transition name="fade">
        <HeureDepartArrivee v-if="showHeureDepartArrivee" />
      </transition>
      <div id="problemepapa">
        <div v-if="probleme" id="Probleme"> Mettre deux gares différentes</div>
      </div>
      <div class="wrapper-button">
        <button @click="navigateToMap" class="btn-neutral">Démarrer</button>
        <button @click="navigateToMap" class="btn-maps">Maps</button>

      </div>
    </div>
  </div>
</template>


<style scoped>
#Probleme {
  background-color: red;
  width: fit-content;
  padding: 0.3rem;
  border-radius: 5px;
  color: #fff;
  text-align: center;
}

/* Style de l'élément parent */
#problemepapa {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}


.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 15px;
  width: fit-content;
  z-index: 1;
  height: fit-content;
  margin: 0;
  justify-content: center;
  background: linear-gradient(135deg, rgb(40, 123, 255, 0.6), rgba(4, 7, 90, 0.78));
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




.dropdown-button {
  padding: 1.2rem 1.2rem;
  background-color: rgb(255, 255, 255);
  color: white;
  border-radius: 2rem;
  border-width: 0;
  background-image: url("../assets/fleche-vers-le-bas.png");
  background-position: center;
  background-size: 1.2rem;
  background-repeat: no-repeat;
  filter: invert(1);
  height: 1rem;
  width: 1rem;
  margin-top: -0.5rem;
  transition: transform 0.3s, background-color 0.3s;
  margin-left: 1rem;
}

.dropdown-button:hover {
  transform: scale(1.05);
}


.dropdown-button-text {
  color: white;
}

.dropdown-button-container {
  right: 0;
  display: flex;
  width: 100%;
  align-items: center;
  position: relative;
  height: 2.5rem;
  /* Par exemple, pour illustrer la hauteur */
}

.dropdown-button-container-sticky {
  display: flex;
  position: absolute;
  top: 0;
  right: 0;
}


body {
  font-family: 'Lato', sans-serif;
}


.green-button {
  display: block;
  width: 250px;
  height: 50px;
  line-height: 50px;
  text-decoration: none;
  background: rgb(124, 164, 236);
  text-align: center;
  color: #ffffff;
  letter-spacing: 1px;
  border: 1px solid;
  transition: all .35s;
  border-radius: 15px;
}

.icon {
  width: 50px;
  height: 50px;
  border: 3px solid transparent;
  position: absolute;
  transform: rotate(45deg);
  right: 0;
  top: 0;
  z-index: -1;
  transition: all .35s;
}

.icon svg {
  width: 30px;
  position: absolute;
  top: calc(50% - 15px);
  left: calc(50% - 15px);
  transform: rotate(-45deg);
  fill: #2ecc71;
  transition: all .35s;
}

.green-button:hover {
  width: 200px;
  border: 3px solid #2ecc71;
  background: transparent;
  color: #2ecc71;
}

.green-button:hover+.icon {
  border: 3px solid #2ecc71;
  right: -25%;
}


.title-trip {
  color: white;
  font-size: 1.2rem;
  border-bottom: black;
  border-bottom: 1px;
  border-radius: 1px;
}

.title-trip-wrapper {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 2rem;
}

.btn-neutral {
  display: inline-block;
  padding: 0.75rem 1.25rem;
  font-size: 1.1rem;
  font-weight: 550;
  text-align: center;
  color: #ffffff;
  background-color: #6a9cf5;

  border-radius: 0.475rem;
  border-color: white;
  border-style: solid;
  border-width: 1px;
  cursor: pointer;
  transition: background-color 0.2s, transform 0.2s;
}

.btn-neutral:hover {
  background-color: black;
  border-color: black;
  transition: border-color 0.2s, transform 0.2s;


}

.btn-neutral:active {
  background-color: black;
  border-color: black;
  transition: border-color 0.2s, transform 0.2s;
  transform: scale(0.95);
}

.btn-maps {
  display: inline-block;
  padding: 0.75rem 1.25rem;
  font-size: 1.1rem;
  font-weight: 550;
  text-align: center;
  color: #ffffff;
  background-color: #6a9cf5;

  border-radius: 0.475rem;
  border-color: white;
  border-style: solid;
  border-width: 1px;
  cursor: pointer;
  transition: background-color 0.2s, transform 0.2s;
  margin-left: 1rem;
} 

.btn-maps:hover {
  background-color: black;
  border-color: black;
  transition: border-color 0.2s, transform 0.2s;


}

.btn-maps:active {
  background-color: black;
  border-color: black;
  transition: border-color 0.2s, transform 0.2s;
  transform: scale(0.95);
}


.wrapper-button {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>