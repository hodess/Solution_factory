<script setup>
import {gares} from "@/stockage/gares";
import HeureDepart from './HeureDepart.vue';
import HeureArrivee from './HeureArrivee.vue';


console.log(gares.value);
import { ref, onMounted } from 'vue';
import DepartDestination from "@/components/DepartDestination.vue";
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


    <div class="Back">
      <div class="title-trip">Heure du trajet</div>
      <div class="button-date-div">
        <button class="date-button">Départ</button>
        <button class="date-button">Arrivée</button>
      </div>


      <HeureDepart/>
      <!-- <HeureArrivee/> -->


      <div class="wrapper">
        <div class="link_wrapper">
          <a href="#">Démarrer</a>
          <div class="icon">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 268.832 268.832">
              <path d="M265.17 125.577l-80-80c-4.88-4.88-12.796-4.88-17.677 0-4.882 4.882-4.882 12.796 0 17.678l58.66 58.66H12.5c-6.903 0-12.5 5.598-12.5 12.5 0 6.903 5.597 12.5 12.5 12.5h213.654l-58.66 58.662c-4.88 4.882-4.88 12.796 0 17.678 2.44 2.44 5.64 3.66 8.84 3.66s6.398-1.22 8.84-3.66l79.997-80c4.883-4.882 4.883-12.796 0-17.678z"/>
            </svg>
          </div>
        </div>

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
    position: relative;
    margin-left: 100%;
    transform: translate(-50%);
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
    position: fixed;
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
      background-color: #e0e0e0; /* Change la couleur de fond au survol */
      transform: scale(1.05);
    }
  }

  .SearchButton{
    margin-top: 1rem;
    display: flex;
    justify-content: center;
  }
</style>
