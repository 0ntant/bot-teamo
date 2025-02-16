--liquibase formatted sql

--changeset 0ntant:10

INSERT INTO  bot_phrases(body, f_type_id, gender) VALUES
('Привет, тебя не учили, что так привлекательно выглядеть — это противозаконно?', 1, 'female'),
('Ты случайно не волшебник? Потому что ты точно заколдовал мое сердце.', 1, 'female'),
('Я не знаю, как ты это делаешь, но с каждым твоим фото я влюбляюсь все больше.', 1, 'female'),
('Не хочешь поучаствовать в исследовании? Нужно узнать, сколько времени потребуется, чтобы ты украл мое сердце.', 1, 'female'),
('Если бы у меня была суперсила, я бы сделала так, чтобы ты оказался рядом.', 1, 'female'),
('Ты всегда такой очаровательный или это только для меня?', 1, 'female'),
('Тебе не кажется, что мы выглядим вместе просто идеально?', 1, 'female'),
('Кажется, я нашла свою слабость... И это ты.', 1, 'female'),
('Ты не знаешь, как мне удалось найти кого-то такого особенного, как ты?', 1, 'female'),
('Кажется, кто-то украл мою улыбку… А потом я поняла, что это был ты.', 1, 'female'),
('Если бы я была астрономом, ты был бы моей любимой звездой.', 1, 'female'),
('Я всегда знала, что у меня хороший вкус, но после того, как увидела тебя, я в этом убедилась окончательно.', 1, 'female'),
('Мне кажется, что ты идеально впишешься в мой вечер.', 1, 'female'),
('Случайно не знаешь, как перестать думать о тебе?', 1, 'female'),
('Твои сообщения делают мой день немного ярче.', 1, 'female'),
('Ты знаешь, что у меня есть суперспособность? Это заметить самого интересного человека в комнате, и это точно ты.', 1, 'female'),
('Не могу перестать улыбаться, когда вижу твое сообщение.', 1, 'female'),
('Кажется, ты читаешь мысли, потому что я только что думала о тебе.', 1, 'female'),
('Тебе стоит быть осторожным — я могу влюбиться.', 1, 'female'),
('Как ты это делаешь? Кажется, с каждым сообщением мое сердце стучит быстрее.', 1, 'female'),
('Тебе не нужно стараться меня очаровать — ты уже это сделал.', 1, 'female'),
('Кажется, я нашла идеального человека для того, чтобы писать ему сообщения ночью.', 1, 'female'),
('Ты можешь передать своим друзьям, что я теперь занята, потому что все мое внимание на тебе.', 1, 'female'),
('У меня такое чувство, что нам нужно познакомиться поближе.', 1, 'female'),
('Я уверена, что ты лучше в реальной жизни, чем на фото, но пока мне и так неплохо.', 1, 'female'),
('Ты, случайно, не украл мои мысли? Потому что я думаю только о тебе.', 1, 'female'),
('Мне кажется, у нас с тобой будет интересная история.', 1, 'female'),
('Ты всегда такой интересный или это только в чате?', 1, 'female'),
('Ты добавляешь в мои дни что-то особенное, и это не только твои сообщения.', 1, 'female'),

('Мне кажется, что с твоим номером в телефоне мои дни станут намного лучше.', 2, 'female'),
('А как я могу тебе звонить, если у меня нет твоего номера?', 2, 'female'),
('Если ты дашь мне свой номер, обещаю не звонить слишком часто... хотя кто знает.', 2, 'female'),
('Не хочешь перейти на более личный уровень общения? Как насчет номера телефона?', 2, 'female'),
('Кажется, нам стоит продолжить это общение в более удобном формате. Напишешь свой номер?', 2, 'female'),
('Я тут подумала, что с твоим номером все было бы намного проще.', 2, 'female'),
('Знаешь, с твоим номером я точно не забуду тебя.', 2, 'female'),
('Если бы ты дал мне свой номер, я бы могла написать тебе что-то еще более интересное.', 2, 'female'),
('Скажи, как мне получить твой номер, чтобы наша переписка стала еще веселее?', 2, 'female'),
('Не оставляй меня в неведении! Дай свой номер, и будем болтать без ограничений.', 2, 'female'),
('Твое сообщение только что скрасило мой день. А можешь скрасить его еще больше, отправив свой номер?', 2, 'female'),
('У меня есть идея: ты дашь мне свой номер, а я сделаю твой день.', 2, 'female'),
('Мне кажется, у тебя есть что-то, что мне очень нужно — твой номер телефона!', 2, 'female'),
('Ты не хочешь дать мне свой номер, чтобы я могла позвонить тебе, когда соскучусь?', 2, 'female'),
('Я уверена, что общение по телефону будет еще лучше, чем в чате. Дашь номер?', 2, 'female'),
('Хочу продолжить наш разговор в голосе, так что, может, поделишься номером?', 2, 'female'),
('Ты же не хочешь, чтобы мы потерялись в этом чате? Дай свой номер.', 2, 'female'),
('Если ты дашь мне свой номер, мы сможем общаться не только здесь.', 2, 'female'),
('С номером телефона все стало бы еще проще и интереснее, не находишь?', 2, 'female'),
('Хочешь обменяться номерами? Я уверена, что в голосе ты звучишь так же круто.', 2, 'female'),
('Дай свой номер, чтобы я могла позвонить тебе, если вдруг захочу услышать твой голос.', 2, 'female'),
('Как ты смотришь на то, чтобы обменяться номерами? У нас явно еще много тем для разговора.', 2, 'female'),
('Случайно не хочешь дать мне свой номер, чтобы я могла написать тебе в любое время?', 2, 'female'),
('Мне кажется, что с твоим номером наше общение выйдет на новый уровень.', 2, 'female'),
('Давай продолжим разговор, но уже по телефону. Напишешь свой номер?', 2, 'female'),
('Я думаю, что мы можем общаться еще лучше по телефону. Дашь номер?', 2, 'female'),
('Мне нужно срочно твой номер. Иначе я просто не выдержу без твоих сообщений.', 2, 'female'),
('Ты не представляешь, насколько было бы проще, если бы у меня был твой номер.', 2, 'female'),
('Мне кажется, что наша переписка требует продолжения... по телефону. Дашь свой номер?', 2, 'female'),
('Нам точно стоит обменяться номерами, ведь общение вживую куда лучше.', 2, 'female'),

('Извини, но, кажется, наши пути расходятся.', 3, 'female'),
('Думаю, нам лучше закончить это общение.', 3, 'female'),
('Я устала от этих разговоров, давай закончим на этом.', 3, 'female'),
('Мне больше неинтересно это обсуждать.', 3, 'female'),
('Пора двигаться дальше, не так ли?', 3, 'female'),
('Я думаю, что у нас разные цели, так что дальше смысла нет.', 3, 'female'),
('Если честно, я больше не вижу смысла продолжать.', 3, 'female'),
('Наша беседа становится скучной, так что лучше закончить.', 3, 'female'),
('Мне кажется, мы оба устали от этих разговоров.', 3, 'female'),
('Извини, но я не заинтересована в дальнейших встречах.', 3, 'female'),
('Давай оставим это в прошлом.', 3, 'female'),
('Может, перестанем мучить друг друга и просто разойдёмся?', 3, 'female'),
('Слушай, мне больше не хочется тратить на это своё время.', 3, 'female'),
('Давай не будем делать вид, что нас ещё что-то связывает.', 3, 'female'),
('Я больше не хочу поддерживать этот разговор.', 3, 'female'),
('Я больше не чувствую связи между нами.', 3, 'female'),
('Тебе не кажется, что пришло время поставить точку?', 3, 'female'),
('С каждым сообщением я убеждаюсь, что нам лучше прекратить общение.', 3, 'female'),
('Мне это больше неинтересно.', 3, 'female'),
('Думаю, что продолжать общение нет смысла.', 3, 'female'),
('У меня больше нет желания поддерживать этот диалог.', 1, 'female'),
('Может, лучше остановимся на этом?', 3, 'female'),
('Извини, но я больше не хочу общаться.', 3, 'female'),
('Кажется, пришло время двигаться дальше, и это точно не вместе.', 3, 'female'),
('Я думаю, нам обоим будет лучше, если мы перестанем общаться.', 3, 'female'),
('Прости, но я больше не хочу тебя видеть.', 3, 'female'),
('Наше общение больше не приносит радости, так что лучше закончить.', 1, 'female'),
('Я не вижу будущего для этого общения.', 3, 'female'),
('Прости, но я решила, что больше не хочу поддерживать контакт.', 3, 'female'),
('Я не готова продолжать отношения в том виде, в каком они есть.', 3, 'female'),

('Привет, ты так сияешь, что мне кажется, будто я смотрю на звезду.', 1, 'male'),
('Скажи, а что нужно сделать, чтобы заслужить твою улыбку?', 1, 'male'),
('Ты не можешь быть настоящей, потому что такие как ты бывают только в мечтах.', 1, 'male'),
('Привет! Если бы красота была преступлением, ты была бы под арестом.', 1, 'male'),
('Мне кажется, мы можем быть отличной командой. Что думаешь?', 1, 'male'),
('Ты, случайно, не художница? Потому что ты только что нарисовала мою мечту.', 1, 'male'),
('Ты не работаешь на Google? Потому что ты нашла всё, что я искал.', 1, 'male'),
('Привет! Если бы я мог написать симфонию, она была бы посвящена твоей красоте.', 1, 'male'),
('Кажется, я потерял что-то важное... Ах, точно, свою способность думать, когда вижу тебя.', 1, 'male'),
('Твоя улыбка точно может спасти этот мир. Что скажешь?', 1, 'male'),
('Мне кажется, мы были бы отличной парой. Не хочешь проверить?', 1, 'male'),
('Привет! Тебе нравится приключения? Давай устроим одно прямо сейчас.', 1, 'male'),
('Как ты думаешь, в каком году изобрели твою привлекательность?', 1, 'male'),
('Ты не устала сегодня? Потому что ты весь день в моих мыслях.', 1, 'male'),
('Знаешь, я уверен, что наше знакомство — лучшее, что могло случиться сегодня.', 1, 'male'),
('Мне кажется, мы бы могли написать отличную историю вместе.', 1, 'male'),
('Ты веришь в судьбу? Потому что наше знакомство явно не случайность.', 1, 'male'),
('С тобой я бы не боялся даже самых сумасшедших идей.', 1, 'male'),
('Привет! Если бы я был художником, ты была бы моим вдохновением.', 1, 'male'),
('С такой улыбкой, как у тебя, невозможно не начать день с хорошего настроения.', 1, 'male'),
('Ты случайно не сошла с обложки журнала? Потому что выглядишь просто идеально.', 1, 'male'),
('Я не могу понять, что красивее: вечерний закат или ты.', 1, 'male'),
('Если бы ты была песней, я бы слушал тебя на повторе.', 1, 'male'),
('Привет! Скажи, а что вдохновляет такую яркую личность, как ты?', 1, 'male'),
('Не знаю как, но ты сделала мой день лучше только своим появлением.', 1, 'male'),
('Твои глаза точно могут рассказать целую историю. Хочешь, я добавлю пару глав?', 1, 'male'),
('Привет! Думаю, что мы с тобой могли бы создать кое-что удивительное.', 1, 'male'),
('Если бы мне пришлось выбрать одно слово, чтобы описать тебя, это было бы "идеальная".', 1, 'male'),
('Ты когда-нибудь задумывалась, насколько ты удивительная?', 1, 'male'),
('Мне кажется, мы были созданы для того, чтобы встретиться именно сегодня.', 1, 'male'),

('Кажется, наши разговоры могут стать еще интереснее по телефону. Напишешь свой номер?', 2, 'male'),
('Как насчет того, чтобы продолжить общение по телефону? Будет быстрее и удобнее.', 2, 'male'),
('Я уверен, что наше общение по телефону будет еще круче. Поделишься номером?', 2, 'male'),
('Давай перейдем на голосовое общение. Напиши свой номер, и я позвоню.', 2, 'male'),
('Мне кажется, телефонный разговор решит все наши вопросы. Напишешь номер?', 2, 'male'),
('Слушай, мне кажется, мы можем болтать бесконечно. Дашь свой номер?', 2, 'male'),
('Если ты не против, я бы хотел получить твой номер, чтобы общаться быстрее.', 2, 'male'),
('Как насчет перейти с переписки на звонки? Дай свой номер.', 2, 'male'),
('Думаю, голосом мы сможем обсудить еще больше интересного. Напишешь номер?', 2, 'male'),
('Мне кажется, что с твоим номером я бы стал чуть счастливее.', 2, 'male'),
('Ты не хочешь дать свой номер, чтобы мы могли общаться еще чаще?', 2, 'male'),
('Я бы с удовольствием услышал твой голос. Напишешь свой номер?', 2, 'male'),
('Давай сделаем наше общение удобнее. Как насчет твоего номера?', 2, 'male'),
('Я уверен, что ты звучишь еще лучше, чем пишешь. Напишешь свой номер?', 2, 'male'),
('Мне кажется, голосовое общение между нами будет еще интереснее. Напишешь номер?', 2, 'male'),
('Можем перенести наш разговор на звонки? Как насчет номера?', 2, 'male'),
('С твоим номером у нас будет еще больше возможностей поговорить.', 2, 'male'),
('Думаю, что наше общение выйдет на новый уровень по телефону. Дашь свой номер?', 2, 'male'),
('Если у тебя есть минутка, давай созвонимся. Дашь номер?', 2, 'male'),
('Я чувствую, что по телефону у нас будет больше свободы для общения. Напишешь номер?', 2, 'male'),
('Полагаю, нам стоит чаще общаться. Может, дашь номер?', 2, 'male'),
('С твоим номером я бы мог сделать наш разговор еще более личным.', 2, 'male'),
('Ты выглядишь так, будто по телефону у тебя еще более приятный голос. Напишешь номер?', 2, 'male'),
('Как насчет позвонить и обсудить все лично? Дай свой номер.', 2, 'male'),
('Думаю, нам пора перейти от сообщений к звонкам. Дашь номер?', 2, 'male'),
('Давай упростим наше общение — напишешь свой номер?', 2, 'male'),
('Мне нравится наша переписка, но голосом было бы еще лучше. Напишешь номер?', 2, 'male'),
('С твоим номером я бы мог позвонить, когда соскучусь.', 2, 'male'),
('Если у нас будет больше времени на общение, я точно захочу звонить. Дашь номер?', 2, 'male'),
('Мне кажется, мы можем поговорить еще дольше по телефону. Дашь свой номер?', 2, 'male'),

('Извини, но мне кажется, что нам пора прекратить это общение.', 3, 'male'),
('Давай будем честны — это ни к чему не ведет.', 3, 'male'),
('Мне больше неинтересно продолжать этот разговор.', 3, 'male'),
('Я думаю, что нам лучше двигаться дальше в разные стороны.', 3, 'male'),
('Кажется, между нами всё уже сказано.', 3, 'male'),
('Наше общение стало утомительным, так что лучше его закончить.', 3, 'male'),
('Я устал от этой бесконечной переписки. Давай закончим.', 3, 'male'),
('Мне это больше не нужно, давай остановимся.', 3, 'male'),
('Я больше не вижу смысла продолжать наше общение.', 3, 'male'),
('Мне кажется, ты больше не даешь мне того, что нужно.', 3, 'male'),
('Честно говоря, я уже потерял интерес.', 3, 'male'),
('Пора признать, что мы тратим время впустую.', 3, 'male'),
('Мне нужно больше личного пространства, давай разойдемся.', 3, 'male'),
('Я думаю, наши разговоры зашли в тупик.', 3, 'male'),
('Извини, но я больше не хочу продолжать общение.', 3, 'male'),
('Давай разойдемся, чтобы не тратить друг на друга больше времени.', 3, 'male'),
('Мне кажется, между нами уже нет ничего общего.', 3, 'male'),
('Я не вижу смысла продолжать общаться, когда всё стало так напряжённо.', 3, 'male'),
('Лучше остановиться сейчас, чем продолжать это бесконечно.', 3, 'male'),
('Прости, но я хочу двигаться дальше без тебя.', 3, 'male'),
('Наше общение перестало приносить радость, давай закончим.', 3, 'male'),
('Я больше не заинтересован в поддержании контакта.', 3, 'male'),
('Это общение стало для меня слишком сложным.', 3, 'male'),
('Давай не будем мучить друг друга и просто прекратим это.', 3, 'male'),
('Мне кажется, что наши пути разошлись.', 3, 'male'),
('Я думаю, что это был последний наш разговор.', 3, 'male'),
('Прости, но я больше не готов к этому общению.', 3, 'male'),
('Наше общение стало слишком напряжённым, так что лучше закончить.', 3, 'male'),
('Это больше не имеет смысла, давай отпустим друг друга.', 3, 'male'),
('Честно, я уже не вижу смысла продолжать общение.', 3, 'male');