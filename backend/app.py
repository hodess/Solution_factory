from flask import Flask, request, render_template

app = Flask(__name__)


def votre_fonction(data1, data2):
    # Implémentez votre algorithme ici
    resultat = [data1, data2, data1 + data2]
    return resultat


@app.route('/')
def index():
    return render_template('index.html')


@app.route('/submit', methods=['POST'])
def submit():
    data1 = request.form['data1']
    data2 = request.form['data2']

    # Assurez-vous que les données sont du bon type
    try:
        data1 = int(data1)
        data2 = int(data2)
    except ValueError:
        return "Veuillez entrer des nombres valides."

    result = votre_fonction(data1, data2)
    return render_template('result.html', result=result)


if __name__ == '__main__':
    app.run(debug=True)
