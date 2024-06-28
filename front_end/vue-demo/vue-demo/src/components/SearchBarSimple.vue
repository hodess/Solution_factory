<template>
  <div class="container">
    <div class="glass-effect">
      <div class="lieu-a" type="depart" >{{ updateStationsSimple }} </div>
      <div class="dotted-lines">
        <div class="dot-line" ref="dotLine1"></div>
        <div class="dot-line" ref="dotLine2"></div>
        <div class="dot-line" ref="dotLine3"></div>
      </div>
      <div class="lieu-b" type="arrivee">{{ updateStationsSimple }}</div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';


const depart = ref('');
const arrivee = ref('');


function updateStationsSimple({ departSelect, arriveeSelect }, type) {
  if (type === "depart"){
    depart.value  = departSelect;
  }else if ( type === "arrivee"){
    arrivee.value = arriveeSelect;
  }
  
}
const dotLine1 = ref(null);
const dotLine2 = ref(null);
const dotLine3 = ref(null);

// Fonction pour animer les points séquentiellement
const animateSequentially = () => {
  animateDot(dotLine1.value, 0);
  setTimeout(() => {
    animateDot(dotLine2.value, 0);
  }, 1600); // Délai plus long pour le deuxième point
  setTimeout(() => {
    animateDot(dotLine3.value, 0);
  }, 3200); // Délai plus long pour le troisième point
};

// Fonction pour animer un point spécifique
const animateDot = (dot, delay) => {
  if (dot) {
    setTimeout(() => {
      dot.classList.add('dot-grow');
      setTimeout(() => {
        dot.classList.remove('dot-grow');
      }, 800); // Durée plus longue pour la réduction
    }, delay);
  }
};

// Démarrer l'animation une fois lorsque le composant est monté
onMounted(() => {
  animateSequentially(); // Démarrer l'animation immédiatement au montage
  setInterval(() => {
    animateSequentially();
  }, 6000); // Répéter l'animation toutes les 6 secondes après le premier démarrage
});
</script>

<style scoped>
.container {
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
  border: 2px solid rgba(255, 255, 255, 1);

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
  align-items: center;
  color: #fff;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
}

.lieu-a, .lieu-b {
  color: white;
  font-size: 1.1rem;
  text-align: left;
}

.dotted-lines{
  margin-left: 2rem;
  margin-top: 1rem;
  margin-bottom: 0.8rem;
  height: 3rem;
}

.dot-line {
  border-radius: 20px;
  width: 0.3rem;
  height: 0.2rem; /* Taille initiale */
  background-color: white;
  margin: 0.5rem 0;
  transition: transform 1s ease-out, background-color 1s ease-out, height 1s ease-out; /* Transition pour l'animation */
}

.dot-grow {
  height: 1rem; /* Taille agrandie */
}
</style>
