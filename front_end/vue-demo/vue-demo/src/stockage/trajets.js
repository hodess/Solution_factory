import {ref} from "vue";

export const stations = [
    {
        "chemins": [
            [
                {
                    "Ligne 5": {
                        "Gare": [
                            {"coord":[50,84],"name":"Gare Montparnasse","id":5},
                            {"coord":[50,84],"name":"Chatelet","id":6}
                        ]
                    },
                    "temps": 180
                },
                {
                    "Ligne 4": {
                        "Gare": [
                            {"coord":[48,88],"name":"Gare du Nord","id":1},
                            {"coord":[50,84],"name":"Gare de Lyon","id":3},
                            {"coord":[50,84],"name":"Chatelet","id":7}
                        ]
                    },
                    "temps": 600
                },
                {
                    "Ligne C": {
                        "Gare": [
                            {"coord":[50,84],"name":"Gare Montparnasse","id":5},
                            {"coord":[48,88],"name":"Gare de l'est","id":4},
                            {"coord":[48,88],"name":"Gare du Nord","id":2}
                        ]
                    },
                    "temps": 760
                }
            ]
        ]
    },
    // Nouveau chemin à ajouter :
    {
        "chemins": [
            [
                {
                    "Ligne 13": {
                        "Gare": [
                            {"coord":[51,85],"name":"Nouvelle Gare","id":8},
                            {"coord":[52,86],"name":"Autre Gare","id":9}
                        ]
                    },
                    "temps": 900
                },
                {
                    "Ligne E": {
                        "Gare": [
                            {"coord":[51,85],"name":"Nouvelle Gare","id":8},
                            {"coord":[52,86],"name":"Autre Gare","id":9}
                        ]
                    },
                    "temps": 300
                },
            ]
        ]
    },
    {
        "chemins": [
            [
                {
                    "Ligne D": {
                        "Gare": [
                            {"coord":[51,85],"name":"Nouvelle Gare","id":8},
                            {"coord":[52,86],"name":"Autre Gare","id":9}
                        ]
                    },
                    "temps": 300
                },
                {
                    "Ligne B": {
                        "Gare": [
                            {"coord":[51,85],"name":"Nouvelle Gare","id":8},
                            {"coord":[52,86],"name":"Autre Gare","id":9}
                        ]
                    },
                    "temps": 1600
                },
            ]
        ]
    }
];

