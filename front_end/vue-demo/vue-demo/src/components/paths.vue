<script>
import { ref } from "vue";
import { stations } from "@/stockage/trajets";

export default {
  setup() {
    // Calculer le temps total des trajets
    const tempsTotal = ref(0);

    // Parcourir les données pour calculer le temps total
    stations.forEach(station => {
      if (station.chemins) {
        station.chemins.forEach(chemin => {
          chemin.forEach(segment => {
            tempsTotal.value += segment.temps;
          });
        });
      }
    });

    return {
      tempsTotal
    };
  }
};
</script>

<template>
  <div class="container">
    <div class="glass-effect">
        <div class="heure">Durée : {{ (tempsTotal / 60).toFixed(0) }} min</div>
        <div class="lignes">
          <img src="../components/icons/arrow.png" alt="Arrow Image" class="fleche" />
      </div>
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
  background: linear-gradient(135deg, rgb(248, 96, 96, 0.5), rgb(218, 3, 3, 0.8));

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

.trajet-impair{
  width: 22vw;
  height: 10vh;
  border-width: 2px;
  background-color: #eaa5a5;
  margin-bottom: 1rem;
  margin-top: 1rem;
  border-color: black;
  border-radius: 1rem;
}

.trajet-pair{
  width: 22vw;
  height: 10vh;
  border-width: 2px;
  background-color: rgba(243, 194, 183, 0.73);
  border-bottom: 2rem;
  border-color: black;
  border-radius: 1rem;
}

img{
  width: 1.5rem;
  height: 1.5rem;
}

.fleche{
  filter: invert(1);
  transform: rotate(-90deg);
  width: 1rem;
  height: 1rem;
  margin-left: 1rem;
  margin-right: 1rem;
}

.heure{
  padding: 0.4rem;

}

.lignes{
  display: flex;
  align-items: center;
  justify-content: center;
}



</style>