<script setup>
</script>


<template>
  <div class="button-date-div">
    <button class="date-button">Départ</button>
    <button class="date-button">Arrivée</button>
  </div>

  <div class="block_input_2">
    <div class="small-title">Date du trajet</div>
    <input
        ref="dateInput"
        @input="updateHomeRouteQuery"
        @keypress.enter="navigateToHomeRoute"
        type="date"
        class="search"
        placeholder="Date"
        :value="currentDate"

    />
    <div class="small-title">Heure de départ</div>
    <input
        ref="timeInput"
        @input="updateHomeRouteQuery"
        @keypress.enter="navigateToHomeRoute"
        type="time"
        class="search"
        placeholder="Heure"
        :value="currentTime"
    />
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

</style>