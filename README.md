# Mobile-Contacts-App
This is Android application, wich access device contacts:
1. App shows list of user contacts. Show just name and phone number in list (RecyclerView).
2. Click on contact item opens "Details" page with additional info (Name, organization, email, phone number)
3. Email and phone are clickable, and click by them opens device email composer or send phone to dial.
4. App represents current charging state at the bottom of screen with image. 
5. Charging state updates dynamically, without app relaunch.
6. Contact list has headers - separators with the first letter of the contact name to group them.
Example:
              А
              Андрей
             Антон
В 
Вадим
Валентин
Д
Дарт Вейдер
etc. 
Display the header only if there are names starting with this letter.
7. App has Collapsing Toolbar with a link to the scroll of ReclyerView. 
In the expanded state, in addition to the title of the screen, 
displays the total number of contacts. 
When scrolling, the toolbar collapses to its normal size and shows only the title of the screen.
8. App has menu in toolbar where you can select sorting (A-Z / Z-A)
