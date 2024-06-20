<script setup>
import { ref } from 'vue';
import DepartDestination from "@/components/DepartDestination.vue";
import HeureArrivee from "@/components/HeureArrivee.vue";
import HeureDepart from "@/components/HeureDepart.vue";

const selectedTime = ref('depart');

const showDepart = () => {
  selectedTime.value = 'depart';
};

const showArrivee = () => {
  selectedTime.value = 'arrivee';
};

// Appel initial pour montrer le composant HeureDepart par défaut
showDepart();
</script>

<template>

  <div class="Back">
    <!-- <div class="title-trip">Heure du trajet</div> -->
    <div class="button-date-div">
      <button
          class="date-button"
          :class="{ 'selected-button': selectedTime === 'depart' }"
          @click="showDepart">Départ</button>
      <button
          class="date-button"
          :class="{ 'selected-button': selectedTime === 'arrivee' }"
          @click="showArrivee">Arrivée</button>
    </div>



    <!-- <transition name="fade">
      <component :is="selectedTime === 'depart' ? HeureDepart : HeureArrivee"/>
    </transition> -->

    <component :is="selectedTime === 'depart' ? HeureDepart : HeureArrivee"/>

    <!-- <transition name="fade" mode="out-in">
      <component :is="selectedTime === 'depart' ? HeureDepart : HeureArrivee"/>
    </transition>
    -->
    <!--  <component :is="selectedTime === 'depart' ? HeureDepart : HeureArrivee"/> -->


    <!-- <HeureDepart/> -->
    <!-- <HeureArrivee/> -->
  </div>


</template>

<style>
.Back {
  flex-direction: column;
  align-items: center;
  flex: 0 0 30%;
  height: 90vh;
  z-index: 5;
  color: var( --vt-c-black);
}

.icon svg{
  width: 30px;
  position: absolute;
  top: calc(50% - 15px);
  left: calc(50% - 15px);
  transform: rotate(-45deg);
  fill: #2ecc71;
  transition: all .35s;
}


.date-button{
  border-radius: 10px;
  font-size: 1rem;
  background-color: whitesmoke;
  padding: 0.5rem;
  width: 8rem;
  border: 0;
}

.button-date-div{
  display: flex;
  margin-left: 5%;
  width: 90%;
  justify-content: space-between; /* Place les composants aux deux extrémités */
  flex-direction: row;
  align-items: center;
  text-align: center;
  position: relative;
  transition: background-color 0.3s, transform 0.3s;
  :hover{
    background-color: #6a9cf5; /* Couleur légèrement grisée */
    color: white;
    transform: scale(1.05);
  }
}


.date-button {
  padding: 10px 20px;
  border: none;
  background-color: #f0f0f0; /* Couleur par défaut */
  cursor: pointer;
  margin: 0 5px;
}

.date-button.selected-button {
  background-color: #6a9cf5; /* Couleur légèrement grisée */
  color: white;
}

.parent {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 3rem;
}

.barre {
  width: 100%;
  height: 2px;
  background-color: white;
}

</style>