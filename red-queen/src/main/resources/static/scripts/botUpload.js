const BASE_URL = window.location.protocol +'//'+ window.location.host + '/red_queen'
const user_create_bot = BASE_URL+'/user-teamo/create/bot';

async function handleFile(event)
{
    bot_id = Number(document.getElementById("id_bot").value)
    email = document.getElementById("email").value
    password = document.getElementById("password").value
    token = document.getElementById("token").value

    if (password == "")
    {
        password = email;
        document.getElementById("email").value=email;
    }

//tovoyo2883@fkcod.com
//21747203
//656a87b97299fae021747203

    var botCreateDto =
    {
        id          : bot_id,
        password    : password,
        email       : email,
        token       : token
    }
   await sendBot(botCreateDto)
}


async function sendBot(sendData)
{
    const url = user_create_bot;
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
         console.log(e);
    }
    console.log(response);

    if (response.ok)
    {
        console.log("Success POST ".concat(url));
        console.log(await response.json());
        window.alert("Post success: " + response.body);

    }
    else {
        console.error("Error POST: ".concat(url));
        console.error(await response.json());
    }
}
