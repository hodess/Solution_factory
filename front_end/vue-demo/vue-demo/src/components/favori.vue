<template>
  <div class="container" v-on:click="handleClick">
    <div class="glass-effect">
      <div class="title-container">
        <img class="étoile-fav" src="./icons/star.png">
        <div class="title">Favori</div>
      </div>

      <div class="lieu-a">{{ mostDepartUsed }}</div>
      <div class="separator"></div>
      <div class="lieu-b">{{ mostArriveeUsed }}</div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
const depart = localStorage.getItem('départ'); // Par exemple: 'gare1,gare2,gare3,gare2'
const arrivee = localStorage.getItem('arrivée'); // Par exemple: 'gare1,gare3,gare2,gare1'
const router = useRouter();
let mostDepartUsed = null;
let mostArriveeUsed = null;

if (depart) {
  mostDepartUsed = findMostUsed(depart);
  //console.log("mostDepart" + mostDepartUsed);
}

if (arrivee) {
  mostArriveeUsed = findMostUsed(arrivee);
}



// Fonction pour trouver l'élément le plus utilisé
function findMostUsed(str) {
  const items = str.split(';'); // Convertit la chaîne en tableau
  const countMap = {};

  // Compte les occurrences de chaque élément
  items.forEach(item => {
    if (countMap[item]) {
      countMap[item]++;
    } else {
      countMap[item] = 1;
    }
  });

  // Trouve l'élément avec le maximum d'occurrences
  let mostUsed = null;
  let maxCount = 0;

  for (const item in countMap) {
    if (countMap[item] > maxCount) {
      maxCount = countMap[item];
      mostUsed = item;

    }
  }

  return mostUsed;
}

// Fonction pour gérer le clic sur le bouton
function handleClick() {
  console.log("test");
  localStorage.setItem('currentDepart', mostDepartUsed);
  localStorage.setItem('currentArrivee', mostArriveeUsed);
  let EnsembleDesDepart = localStorage.getItem('départ')
  let EnsembleDesArrivee = localStorage.getItem('arrivée')
  EnsembleDesDepart += ";" + mostDepartUsed
  EnsembleDesArrivee += ";" + mostArriveeUsed
  localStorage.setItem('départ', EnsembleDesDepart);
  localStorage.setItem('arrivée', EnsembleDesArrivee);
  router.push('/map');

}

</script>

<style scoped>
.container {
  cursor: pointer;
  flex-direction: column;
  align-items: center;
  border-radius: 15px;
  width: fit-content;
  z-index: 1;
  height: fit-content;

  margin: 0;
  justify-content: center;
  background: linear-gradient(135deg, rgba(255, 169, 40, 0.826), rgba(155, 109, 3, 0.832));
  border: 2px solid rgba(255, 255, 255, 1);
}

.étoile-fav{
  width: 1.5rem;
  height: 1.5rem;
}

.title-container{
  display: flex;
  flex-direction: row;
  width: fit-content;
  margin-bottom: 1rem;
}

.title {
  margin-top: -0.2rem;
  margin-left: 0.5rem;
  text-align: center;
  font-size: 1.1rem;
  flex: 1;
}

.glass-effect {
  border-radius: 15px;
  padding: 2rem;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(2px);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1), 0 6px 20px rgba(0, 0, 0, 0.1);
  align-items: center;
  color: #fff;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
}

.lieu-a,
.lieu-b {
  color: white;
  font-size: 0.9rem;
  text-align: left;
}

.separator {
  color: red;
  display: inline-block;
  margin-top: 0.3rem;
  margin-bottom: 0.3rem;
  margin-left: 1rem;
  border-radius: 1rem;
  width: 4%;
  height: 2rem;
  background-color: white; /* Couleur de la barre blanche */
  vertical-align: middle;
}


</style>
