<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios'; 

import DepartDestination from "@/components/DepartDestination.vue";
import HeureDepartArrivee from "@/components/HeureDepartArrivee.vue";

const svgElementRef = ref(null);
const router = useRouter();

let Depart = ref(DepartDestination.searchTextDepart);
let Arrivee = ref(DepartDestination.searchTextArrive);

onMounted(() => {
  if (svgElementRef.value) {
    svgElementRef.value.addEventListener("click", function() {
      console.log(DepartDestination);
      console.log(Depart.value);
      console.log(Arrivee);
    });
  }
});

const dropdownButton = ref(null);
const handleClick = () => {
  toggleHeureDepartArrivee();
  rotateButton();
};

const selectedTime = ref('depart');
const showHeureDepartArrivee = ref(false);
const toggleHeureDepartArrivee = () => {
  showHeureDepartArrivee.value = !showHeureDepartArrivee.value;
};

const showDepart = () => {
  selectedTime.value = 'depart';
  rotateButton();
};

const showArrivee = () => {
  selectedTime.value = 'arrivee';
  rotateButton();
};

const rotateButton = () => {
  if (dropdownButton.value) {
    dropdownButton.value.style.transition = 'transform 0.3s, background-color 0.3s';

    if (!dropdownButton.value.classList.contains('rotated')) {
      dropdownButton.value.style.transform = 'rotate(180deg)';
      dropdownButton.value.classList.add('rotated');
    } else {
      dropdownButton.value.style.transform = '';
      dropdownButton.value.classList.remove('rotated');
    }
  }
};

const navigateToMap = () => {
  router.push('/map');
};
</script>

<template>
  <header class="navbar">
    <div class="logo-locomotive"/>
    <div class="title-locomotive">Locomotive</div>
    <div class="bloc-right"/>
  </header>

  <body>
    <div class="container">
      <DepartDestination/>
      <div class="dropdown-button-container">
        <div class="dropdown-button-container-sticky">
          <div class="dropdown-button-text">Quand :</div>
          <button  ref="dropdownButton" @click="handleClick" class="dropdown-button"></button>
        </div>
      </div>
      <transition name="fade">
        <HeureDepartArrivee v-if="showHeureDepartArrivee"/>
      </transition>
      <div class="wrapper">
        <div class="link_wrapper">
          <button @click="navigateToMap">Démarrer</button>
        </div>
      </div>
    </div>
  </body>
</template>

<style scoped>
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



    .dropdown-button {
      padding: 1.2rem 1.2rem;
      background-color: rgb(255, 102, 102);
      color: white;
      border-radius: 15px;
      border-width: 0;
      background-image: url("../assets/fleche-vers-le-bas.png");
      background-position: center;
      background-size: 1.2rem;
      background-repeat: no-repeat;
      height: 1rem;
      width: 1rem;
      margin-top: -0.5rem;
      transition: transform 0.3s, background-color 0.3s;
      margin-left: 1rem;
    }
    .dropdown-button:hover {
      transform: scale(1.05);
    }


    .dropdown-button-text{
      color: white;
    }

    .dropdown-button-container{
      right: 0;
      display: flex;
      width: 100%;
      align-items: center;
      position: relative;
      height: 2.5rem; /* Par exemple, pour illustrer la hauteur */
    }

    .dropdown-button-container-sticky{
      display: flex;
      position: absolute;
      top: 0;
      right: 0;
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
      transform: translate(-50%);

    }

    .link_wrapper{
      position: relative;
      margin-right: 2.75rem;
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
      border: 1px solid;
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



  </style>
