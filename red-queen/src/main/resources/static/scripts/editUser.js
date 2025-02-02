const BASE_URL_RQ = window.location.protocol +'//'+ window.location.host + '/red_queen'
const order_user_url = BASE_URL+'/user-teamo/edit/user';

async function editUser(userId)
{
    sendRequest(order_user_url + "/" + userId)
}

async function sendRequest(url)
{
    var userDto = {
        "bot_writable" : document.getElementById("bot_writable").checked
    }

    try
    {
        const response = await fetch
            (url, {
                method: "POST",
                credentials: 'include',
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(userDto)
            });
    }catch(e)
    {
         console.log(e);
    }
    console.log(response);
}