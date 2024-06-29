import { reactive } from 'vue';

// Créer un objet réactif pour servir de bus d'événements global
const eventBus = reactive({
    events: {}
});

// Méthode pour émettre des événements
eventBus.emit = function(event, ...args) {
    if (!this.events[event]) return;
    this.events[event].forEach(callback => callback(...args));
};

// Méthode pour écouter des événements
eventBus.on = function(event, callback) {
    if (!this.events[event]) this.events[event] = [];
    this.events[event].push(callback);
};

export default eventBus;
