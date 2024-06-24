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
      /*
      background-color: var(--vt-c-container-color) ;
      background-color: rgba(255, 137, 0, 0.62);
      background-color: rgba(106, 156, 245, 0.89);
      background: #6a9cf5;*/

      width: fit-content;
      z-index: 1;
      height: fit-content;

      margin: 0;
      justify-content: center;
      background: linear-gradient(135deg, rgb(40, 123, 255, 0.6), rgba(4, 7, 90, 0.78));
/*
      background-color: linear-gradient(135deg, #00b4db, #0083b0);
      background-color: rgb(40, 123, 255, 0.1);
      backdrop-filter: blur(5px);
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1), 0 6px 20px rgba(0, 0, 0, 0.1);
      border: 1px solid rgb(255, 255, 255);
      color: rgba(255, 137, 0, 0.74);
      text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);*/

    }



    .glass-effect{
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

    .green-button{

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

    .green-button:hover{
      width: 200px;
      border: 3px solid #2ecc71;
      background: transparent;
      color: #2ecc71;
    }

    .green-button:hover + .icon{
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

    .title-trip-wrapper{
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      width: 100%;
      margin-bottom: 2rem;
    }



  </style>
