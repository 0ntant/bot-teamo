const BASE_URL = window.location.protocol +'//'+ window.location.host + '/red_queen'
const bot_phrase_create = BASE_URL+'/bot-phrases/create';

async function handleClick(event)
{
    body    = document.getElementById("bot_phrase_body").value
    gender  = document.getElementById("gender").value
    type_id = Number(document.getElementById("bot_phrase_type_id").value)
        var phraseDto =
        {
            body  : body,
            gender: gender,
            type  :{
                    id : type_id
                }
        }
    await sendBot(phraseDto)
}


async function sendBot(sendData)
{
    const url = bot_phrase_create
    console.log(url)
    try
    {
        const response = await fetch
            (url, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(sendData)
            });
    }catch(e) {
         console.log(e)
    }
    console.log(response)

    if (response.ok)
    {
        console.log("Success POST ".concat(url))
        console.log(await response.json())
        window.alert("Post success: " + response.body)

    }
    else {
        console.error("Error POST: ".concat(url))
        console.error(await response.json())
    }
}