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

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 15px;
  background-color: var(--vt-c-container-color) ;
  padding: 2rem;
  margin-top: 10rem;
  width: fit-content;
  box-shadow: 0 2px 4px var( --vt-c-black);
  z-index: 1;
}

.Back {
  flex-direction: column;
  align-items: center;
  flex: 0 0 30%;
  height: 90vh;
  z-index: 5;
  color: var( --vt-c-black);
}





button {
  background-color: var(--vt-c-white);
  color: var( --vt-c-black);
  padding: 1rem 2rem;
  border-radius: 2rem;
  margin-top: 1rem;
  font-size: 1.5rem;
}

.navbar {
  background-color: var(--vt-c-navbar-color);
  color: var(--vt-c-white);
  padding: 1rem;
  left: 0;
  top: 3.5vh;
  transform: translateY(-50%);
  align-items: center;
  text-align: center;
  font-size: 1.5rem;
  font-weight: bold;
  position: fixed;
  display: flex;
  width: 100%;
  box-shadow: 0 2px 4px var( --vt-c-black);
  z-index: 3;
}
.logo-locomotive{
  background-image: url("@/assets/logo-locomotive.png");
  background-size: cover; /* Pour ajuster la taille de l'image à la fenêtre */
  background-position: center;
  z-index: -0.5;
  border-radius: 15px;
  height: 5rem;
  width: 5rem;
  margin-top: 3rem
}
.bloc-right{
  height: 5rem;
  width: 5rem;
  margin-top: 3rem
}

.title-locomotive {
  margin-top: 3rem;
  font-size: 1.8rem;
  flex: 1;
}


.title-trip{
  color: #f8f8f8;
  font-size: 1.2rem;
}
body{
  font-family: 'Lato', sans-serif;
}

.wrapper{
  place-items: center;
  margin-left: 1.25rem;
  display: flex;
  position: relative;
  top: 90%;
  left: 50%;
  transform: translate(-50%, -50%);

}

.link_wrapper{
  position: relative;
}

a{
  display: block;
  width: 250px;
  height: 50px;
  line-height: 50px;
  font-weight: bold;
  text-decoration: none;
  background: #6a9cf5;
  text-align: center;
  color: #ffffff;
  text-transform: uppercase;
  letter-spacing: 1px;
  border: 3px solid;
  transition: all .35s;
  border-radius: 15px;
}

.icon{
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

.icon svg{
  width: 30px;
  position: absolute;
  top: calc(50% - 15px);
  left: calc(50% - 15px);
  transform: rotate(-45deg);
  fill: #2ecc71;
  transition: all .35s;
}

a:hover{
  width: 200px;
  border: 3px solid #2ecc71;
  background: transparent;
  color: #2ecc71;
}

a:hover + .icon{
  border: 3px solid #2ecc71;
  right: -25%;
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