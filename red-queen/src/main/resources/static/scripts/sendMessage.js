const BASE_URL = window.location.protocol +'//'+ window.location.host + '/red_queen'
const user_create_bot = BASE_URL+'/user-teamo/send-message';

async function handleFile(event)
{
    user_with_token_id = Number(document.getElementById("users_with_token_ids").value)
    user_receiver_id   = Number(document.getElementById("users_receiver_ids").value)
    message            = document.getElementById("message").value

    var botCreateDto =
    {
        userWithTokenId : user_with_token_id,
        userReceiverId  : user_receiver_id,
        message         : message
    }
    await sendBot(botCreateDto)
}

async function sendBot(sendData)
{
    const url = user_create_bot
    console.log(url)
    try
    {
        const response = await fetch
            (url, {
                method: "POST",
                credentials: 'include',
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
    else
    {
        console.error("Error POST: ".concat(url))
        console.error(await response.json())
    }
}