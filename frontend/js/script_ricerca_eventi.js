const URL = "http://localhost:8080/event/events";
const PADRE = document.getElementById("lista-eventi");

function stampaEventi(eventi){
    PADRE.innerHTML = ""
    console.log(eventi.lenght)
    if (eventi.length == 0 ){
        PADRE.innerHTML = `<h1 style="text-align: center; margin-top:20px;">Non ci sono eventi</h1>`
    }
    else{
        eventi.forEach(element => {
            
        const DIV = document.createElement('div');
        DIV.setAttribute('class','ricerca-evento');
        DIV.onclick = function () {
            localStorage.setItem('id_evento',element['id_evento'])
            window.location.href = 'schermata_evento.html'
        }

        const ICON = document.createElement('div');
        const IMG = document.createElement('img');
        IMG.setAttribute('src',`../img/${img_categoria(element.categoria)}`)
        ICON.setAttribute('class', 'ricerca-icona-evento');
        ICON.appendChild(IMG)
        
        const DETTAGLI = document.createElement('div');
        DETTAGLI.setAttribute('class','ricerca-dettagli-evento');
        const TITOLO = document.createElement('p');
        TITOLO.setAttribute('class','ricerca-titolo-evento');
        TITOLO.innerHTML=element.titolo;
        DETTAGLI.appendChild(TITOLO);
        let PAR;
        PAR = creaParagrafo(`Data inizio: ${gestioneData(element.data_inizio)}`);
        DETTAGLI.appendChild(PAR);
        PAR = creaParagrafo(`Partecipanti max: ${element.n_max_partecipanti}`);
        DETTAGLI.appendChild(PAR);
        const pos = gestionePosizione(element.posizione);
        PAR = creaParagrafo(`Posizione: ${pos[0]}`);
        DETTAGLI.appendChild(PAR);
        PAR = creaParagrafo(`${pos[1]}`);
        DETTAGLI.appendChild(PAR);
        PAR = creaParagrafo(`Host: ${element.nome_host} ${element.cognome_host}`);
        DETTAGLI.appendChild(PAR);
        
        DIV.appendChild(ICON);
        DIV.appendChild(DETTAGLI)
        PADRE.appendChild(DIV)
        });
    }
}


fetch (URL)
    .then (eventi => eventi.json())
    .then (eventi => {
        console.log(eventi)
        
        stampaEventi(eventi)
        
    })

function creaParagrafo(testo){
    const PAR = document.createElement('p')
    PAR.setAttribute('class','ricerca-testo-evento');
    PAR.innerHTML = testo;
    return PAR;
}

async function filtraEventi(){
    event.preventDefault();
    const PERS = document.getElementById("persona").value;
    const CITTA = document.getElementById("citta").value;
    const DATA = document.getElementById("data").value;
    const CAT = document.getElementById("categoria").value;

    const data = {};
    if (PERS != ""){
        data.host = PERS;
    }
    if (CITTA!= ""){
        data.citta = CITTA;
    }
    if (DATA!= ""){
        data.data = DATA;
    }
    if (CAT!= ""){
        data.categoria = CAT;
    }

    console.log(data)

    const ris = await fetch(`${URL}/filtri`,{
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    const eventi = await ris.json()
    console.log(eventi)
    stampaEventi(eventi)
}