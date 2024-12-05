const BASE_URL = window.location.protocol +'//'+ window.location.host + '/red_queen'
const user_create_bot = BASE_URL+'/user-teamo/send-message';
const user_get_users_data = BASE_URL+'/user-teamo/get/user-chat-partners/';
const user_get_chat = BASE_URL + '/teamo-message/get/chat/';

async function handleClick(event)
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

async function handleChangeSelect2()
{
      user_with_token_id = Number(document.getElementById("users_with_token_ids").value)
      user_receiver_id   = Number(document.getElementById("users_receiver_ids").value)

      chat = await getChat (user_with_token_id , user_receiver_id)
      await createChat(chat)
}

async function handleChangeSelect()
{
      users_with_token_id = Number(document.getElementById("users_with_token_ids").value)
      console.log(users_with_token_id)
      users_receiver = await getUsersData (user_get_users_data + users_with_token_id)
      createOptionsSelect(users_receiver)

      user_with_token_id = Number(document.getElementById("users_with_token_ids").value)
      user_receiver_id   = Number(document.getElementById("users_receiver_ids").value)

      chat = await getChat (user_with_token_id , user_receiver_id)
      await createChat(chat)
}

function getChat(sender_id, receiver_id)
{
    return getUsersData(user_get_chat+sender_id+"/"+receiver_id)
}

function createChat(messages)
{
     document.getElementById("chat").innerHTML = "";
     var chatElement = document.getElementById("chat");
      console.log(messages.length)
     for (i = 0; i < messages.length; ++i)
     {
     console.log("hm")
        let li = document.createElement('li');
        li.innerText = messages[i].id + " " + messages[i].senderName + " "
                       + messages[i].body + " " + messages[i].date;
        chatElement.appendChild(li);
     }
}

async function createOptionsSelect(users_receiver)
{
    removeOptions(document.getElementById('users_receiver_ids'));
    //var myParent = document.body;

    //Create and append select list
    var selectList = document.getElementById("users_receiver_ids");
    //myParent.appendChild(selectList);

    //Create and append the options
    for (var i = 0; i < users_receiver.length; i++) {
        var option = document.createElement("option");
        option.value = users_receiver[i].id;
        option.text = users_receiver[i].id + " " + users_receiver[i].name +" "+ users_receiver[i].city;
        selectList.appendChild(option);
    }
}

function removeOptions(selectElement) {
   var i, L = selectElement.options.length - 1;
   for(i = L; i >= 0; i--) {
      selectElement.remove(i);
   }
}

async function getUsersData(url)
{
   const response = await fetch
        (url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

   if (response.ok)
   {
      var data = await response.json()
      console.log( data)
      return data
   }
    else
    {
        console.error("Error GET: ".concat(url))
        console.error(await response.json())
    }
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