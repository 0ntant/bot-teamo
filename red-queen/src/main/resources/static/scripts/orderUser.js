const BASE_URL = window.location.protocol +'//'+ window.location.host + '/red_queen'
const user_create_order = BASE_URL+'/user-teamo/create/order';

async function copyUser(userId)
{
    executePost(BASE_URL + "/user-teamo/create/clone/user/" + userId)
}

async function executePost(url)
{
    try
    {
        const response = await fetch
            (url, {
                method: "POST",
                credentials: 'include',
                headers: {
                    "Content-Type": "application/json"
                }
            });
    }
    catch(e)
    {
         console.log(e)
    }
}

async function handleClick(event)
{
    user_name = document.getElementById("user_name").value
    genders = document.getElementsByName("gender")

    for (var i = 0, length = genders.length; i < length; i++)
    {
        if (genders[i].checked)
        {
            gender =  genders[i].value
        }
    }

    age   = Number(document.getElementById("age").value)
    city  = document.getElementById("city").value
    height = Number(document.getElementById("height").value)

    var createOrderDto =
    {
        name    : user_name,
        gender  : gender,
        age     : age,
        city    : city,
        height  : height
    }
    await sendOrder(createOrderDto)
}


async function sendOrder(sendData)
{
    const url = user_create_order
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