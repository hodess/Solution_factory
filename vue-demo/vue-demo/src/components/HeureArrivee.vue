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

.block_input_2{
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 5rem;
}

.date-button{
  border-radius: 10px;
  font-size: 1rem;
  background-color: whitesmoke;
  padding: 0.5rem;
  width: 8rem;
  border: 0;
}

.block_input{
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 2rem;
}


button {
  background-color: var(--vt-c-white);
  color: var( --vt-c-black);
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
  color: var( --vt-c-black);
  align-items: center;
  width: 20rem;
  border-width: 0;
}

</style>