const BASE_URL = window.location.protocol +'//'+ window.location.host + '/img_generator'
const IMG_DTO_URL = BASE_URL + "/v2/img"

var data;

async function sendImg()
{
    var url = IMG_DTO_URL + '/create'
    try
    {
        const response = await fetch
            (url, {
                method: "POST",
                headers: {
                   "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            });
    }
    catch(e)
    {
        console.log(e)
    }
    await getImgDataDto()
}

async function deleteImg()
{
    var url = IMG_DTO_URL + '/delete'
    try
    {
        const response = await fetch
            (url, {
                method: "DELETE",
                headers: {
                   "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            });
    }
    catch(e)
    {
        console.log(e)
    }
    await getImgDataDto()
}

async function getImgDataDto()
{
    var url = IMG_DTO_URL + '/get/rand';

    try
    {
        const response = await fetch(url, {
            method: "GET",
        });
        data = await response.json();
        console.log(data);
        setImg();
    }
       catch (e)
    {
       console.log('Error:', e);
    }
}

async function setImg()
{
       const img = document.getElementById('get_img');
       img.src = `data:image/jpeg;base64,${data.imgData}`;

       const source =  document.getElementById('source');
       source.innerHTML = data.source;
}

