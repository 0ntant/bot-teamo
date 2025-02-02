const BASE_URL = window.location.protocol +'//'+ window.location.host + '/red_queen'
const bot_phrase_type_create = BASE_URL+'/bot-phrases-type/create';

async function handleFile(event)
{
    title = document.getElementById("title").value
    var botPhrasesTypeCreateDto =
    {
        title : title
    }
   await sendBot(botPhrasesTypeCreateDto)
}

async function sendBot(sendData)
{
    const url = bot_phrase_type_create
    console.log(url)
    const cookies = document.cookie;
    try
    {
        const response = await fetch
            (url, {
                method: "POST",
                credentials: "include",
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