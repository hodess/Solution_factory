<script setup>
import { ref, watch } from "vue";


// Fonction pour obtenir la date actuelle au format ISO (YYYY-MM-DD)
const getCurrentDate = () => {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

// Variable réactive pour stocker la date actuelle
const currentDate = ref(getCurrentDate());
const minDate = getCurrentDate(); // Date minimale pour l'input


// Fonction pour ajouter 1 jour à la date actuelle
const addOneDay = () => {
  const date = new Date(currentDate.value);
  date.setDate(date.getDate() + 1);
  currentDate.value = date.toISOString().split('T')[0];
};

// Fonction pour soustraire 1 jour à la date actuelle
const suprOneDay = () => {
  const date = new Date(currentDate.value);
  date.setDate(date.getDate() - 1);
  currentDate.value = date.toISOString().split('T')[0];
};

// Empêcher de définir une date antérieure à la date actuelle
watch(currentDate, (newDate, oldDate) => {
  if (newDate < minDate) {
    currentDate.value = minDate;
  }
});

// Fonctions pour les événements de mise à jour de la route et de navigation
const updateHomeRouteQuery = () => {
  // Mettre à jour la route ou effectuer d'autres actions lorsque les valeurs changent
};

const navigateToHomeRoute = () => {
  // Naviguer vers la route d'accueil ou effectuer d'autres actions lorsque la touche Entrée est enfoncée
};

// Fonction pour obtenir l'heure actuelle au format HH:MM
const getCurrentTime = () => {
  const now = new Date();
  const hour = String(now.getHours()).padStart(2, '0');
  const minute = String(now.getMinutes()).padStart(2, '0');
  return `${hour}:${minute}`;
};

// Variable réactive pour stocker l'heure actuelle
const currentTime = ref(getCurrentTime());
sessionStorage.setItem('currentTime', currentTime.value);
sessionStorage.setItem('type_Time', 1);


watch(currentTime, (newTime) => {
  console.log(newTime, currentTime.value)
  sessionStorage.setItem('currentTime', newTime);
  sessionStorage.setItem('type_Time', 1);

});
</script>



<template>


  <div class="block_input_2">
    <div class="small-title">Date d'arrivée :</div>

    <div class="conteneur-fleche">
      <div class="flèche-gauche" @click="suprOneDay"></div>
      <input ref="dateInput" @input="updateHomeRouteQuery" @keypress.enter="navigateToHomeRoute" type="date"
        class="search" placeholder="Date" :value="currentDate" :min="minDate" />
      <div class="flèche-droite" @click="addOneDay"></div>
    </div>
    <div class="small-title">Heure d'arrivée :</div>
    <input ref="timeInput" @input="updateHomeRouteQuery" @keypress.enter="navigateToHomeRoute" type="time"
      class="search" placeholder="Heure" v-model="currentTime" />
    <!--
    <div class="small-title">Heure d'arrivée</div>
    <input
        ref="timeInput"
        @input="updateHomeRouteQuery"
        @keypress.enter="navigateToHomeRoute"
        type="time"
        class="search"
        placeholder="Heure"
    />-->
  </div>

</template>


<style scoped>
.block_input_2 {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 3rem;
}

.date-button {
  border-radius: 10px;
  font-size: 1rem;
  background-color: whitesmoke;
  padding: 0.5rem;
  width: 8rem;
  border: 0;
}

.block_input {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 2rem;
}


button {
  background-color: var(--vt-c-white);
  color: var(--vt-c-black);
  padding: 1rem 2rem;
  border-radius: 2rem;
  margin-top: 1rem;
  font-size: 1.5rem;
}

.search {
  flex-grow: 1;
  min-width: 0;
  padding: 0.5rem 1rem;
  border-radius: 0.5rem;
  background-color: #f8f8f8;
  height: 3.5rem;
  margin-top: 1rem;
  font-size: 1rem;
  color: var(--vt-c-black);
  align-items: center;
  width: 20rem;
  border-width: 0;
}

.small-title {
  margin-top: 1rem;
  margin-bottom: -0.8rem;
  color: #f8f8f8;
}

.conteneur-fleche {
  display: flex;
  flex-direction: row;
  justify-content: space-between;


  width: 100%;
  align-items: center;
  text-align: center;
  position: relative;


  .flèche-droite {
    background-color: #6a9cf5;
    height: 2rem;
    width: 2rem;
    border-radius: 5px;
    margin-top: 1rem;
    margin-left: 1rem;
    background-image: url("@/assets/fleche-vers-le-bas.png");
    background-repeat: no-repeat;
    background-size: 50% 50%;
    background-position: center;
    transform: rotate(-90deg);
    transition: background-color 0.3s, transform 0.3s;
  }

  .flèche-droite:hover {
    transform: scale(1.2) rotate(-90deg);
  }

  .flèche-gauche {
    background-color: #6a9cf5;
    height: 2rem;
    width: 2rem;
    border-radius: 5px;
    margin-top: 1rem;
    margin-right: 1rem;
    background-image: url("@/assets/fleche-vers-le-bas.png");
    background-repeat: no-repeat;
    background-size: 50% 50%;
    background-position: center;
    transform: rotate(90deg);
    transition: background-color 0.3s, transform 0.3s;
  }

  .flèche-gauche:hover {
    transform: scale(1.2) rotate(90deg);
  }

  input {
    width: 50%;
  }
}
</style>