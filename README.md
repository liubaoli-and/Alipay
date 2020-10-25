# Alipay

​    The alipay program simulates the transfer of Alipay funds to a bank card. The program calculates the handling fee that the transfer party needs to provide based on the personal account information of the user, the transfer party. Handling fees need to be paid by the transfer party.

1. input specifications

| Inputs             | type    | Description                                                  | Restriction          |
| ------------------ | ------- | ------------------------------------------------------------ | -------------------- |
| isVerified         | boolean | Whether the transfer party is real-name certified            |                      |
| transferInBalance  | double  | account balance assets of the transferor                     |                      |
| transferInYebao    | double  | Transfer party account Yu'ebao assets                        |                      |
| transferInBandCard | double  | Transfer party account bank card assets                      |                      |
| freeTransferLimit  | double  | The transferor's free transfer limit                         |                      |
| transferMethod     | int     | transfer method <br>1 Transfer from balance<br/>2 Transfer from Yu'e Bao<br/>3 Transfer from the bound bank card/credit card | only numbers 1, 2, 3 |
| transfereeName     | String  | The name of the transferee                                   |                      |
| BandCard           | String  | Bank account number of the transferee                        |                      |
| cardId             | boolean | Bank card number of the transfer party                       |                      |
| transferAmount     | Double  | transfer amount                                              |                      |

2. output specifications
   The output parameters are "handlingFee", which type is double. 

3. Description of MRs
   The service interface tested this time is TransfersCard. Twenty transformation relationships are designed in the design MRs. The specific expression of the transformation relationship is shown in the table.

   <table><tr><td>No</td><td>R</td><td>Rf</td></tr>
   <tr><td colspan='3'><center>1.isVerified=false, transferMethod = 1</center></td></tr>
   <tr><td>MR1</td><td>transferAmount_=transferAmount+1<br> (1.02*transferAmount<=moneyInBalance-1.02)</td><td>handlingFee_=handlingFee+0.02</td></tr> 
       <tr><td>MR2</td><td> transferAmount_=transferAmount+1<br>(transferInBalance<1.02*transferAmount)</td><td>handlingFee_=handlingFee=-1</td></tr>
   <tr><td colspan='3'><center>2.isVerified=false, transferMethod = 2</center></td></tr>
       <tr><td>MR3</td><td>transferAmount_=transferAmount+1<br>(moneyInYuebao-1.02>=1.02*transferAmount)</td><td> handlingFee_=handlingFee+0.02</td></tr>
       <tr><td>MR4</td><td>transferAmount_=transferAmount+1<br>(transferInYuebao &lt;transferAmount*1.02)</td><td>handlingFee_=handlingFee=-1</td></tr>
       <tr><td colspan='3'><center>3.isVerified=false, transferMethod = 3</center></td></tr>
       <tr><td>MR5</td><td>transferAmount_=transferAmount+1</td><td>handlingFee_=handlingFee=-1</td></tr>
       <tr><td colspan='3'><center>4.isVerified=true, actualName!=name,transferMethod =1</center></td></tr>
       <tr><td>MR6</td><td>transferAmount_=transferAmount+1<br>(transferInBalance  &lt;transferAmount)</td><td>handlingFee_=handlingFee=-1</td></tr>
       <tr><td>MR7</td><td> transferAmount_=transferAmount+1<br>(transferAmount<=freeTransferLimit-1<&&<br>transferAmount<=transferInBalance-1)</td><td>handlingFee_=handlingFee=0</td></tr>
       <tr><td>MR8</td><td>transferAmount_=transferAmount+1<br>(transferAmount>freeTransferLimit&&<br>1.02*transferAmount<=transferInBalance+0.02*freeTransferLimit-1.02)</td><td>handlingFee_=handlingFee+0.02</td></tr>
          <tr><td>MR9</td><td>transferAmount_=transferAmount+1<br>(transferAmount>freeTransferLimit&&transferAmount<=transferInBalance-1&&<br>1.02*transferAmount>transferInBalance+0.02*freeTransferLimit)</td><td>handlingFee_=handlingFee=-1</td></tr>
       <tr><td colspan='3'><center>5.isVerified=true, actualName!=name,transferMethod =2</center></td></tr>
          <tr><td>MR10</td><td>transferAmount_=transferAmount+1<br>(transferInYuebao &lt;transferAmount)</td><td>handlingFee_=handlingFee=-1</td></tr> 
       <tr><td>MR11</td><td>transferAmount_=transferAmount+1<br>(transferInYuebao>=transferAmount+1)</td><td>handlingFee_=handlingFee=0</td></tr>
       <tr><td colspan='3'><center>6.isVerified=true, actualName!=name,transferMethod =3</center></td></tr>
          <tr><td>MR12</td><td>transferAmount_=transferAmount+1<br>(transferInBandCard &lt;transferAmount)</td><td>handlingFee_=handlingFee=-1</td></tr>
          <tr><td>MR13</td><td>transferAmount_=transferAmount+1<br>(transferAmount<=freeTransferLimit-1&&transferAmount<=transferInBandCard-1)</td><td>handlingFee_=handlingFee=0</td></tr>
          <tr><td>MR14</td><td> transferAmount_=transferAmount+1<br>(transferAmount>=freeTransferLimit&&<br>1.02*transferAmount<=transferInBandCard+0.02*freeTransferLimit-1.02)</td><td>handlingFee_=handlingFee+0.02</td></tr>
          <tr><td>MR15</td><td>transferAmount_=transferAmount+1<br>(transferAmount>freeTransferLimit&&transferAmount<=transferInBandCard-1&&<br>1.02*transferAmount>transferInBandCard+0.02*freeTransferLimit)</td><td>handlingFee_=handlingFee=-1</td></tr>
       <tr><td colspan='3'><center>7.isVerified=true, actualName=name,transferMethod =1</center></td></tr>
          <tr><td>MR16</td><td> transferAmount_=transferAmount+1<br>(transferInBalance &lt;transferAmount)</td><td>handlingFee_=handlingFee=-1</td></tr> 
       <tr><td>MR17</td><td> transferAmount_=transferAmount+1<br>(transferInBalance>=transferAmount+1)</td><td> handlingFee_=handlingFee=0</td></tr>
       <tr><td colspan='3'><center>8.isVerified=true, actualName=name,transferMethod =2</center></td></tr>
          <tr><td>MR18</td><td>transferAmount_=transferAmount+1<br>(transferInYuebao &lt;transferAmount)</td><td> handlingFee_=handlingFee=-1</td></tr>
          <tr><td>MR19</td><td> transferAmount_=transferAmount+1<br>(transferInYuebao>=transferAmount+1)</td><td>handlingFee_=handlingFee=0</td></tr> 
       <tr><td colspan='3'> <center>9.isVerified=true, actualName=name,transferMethod =3,bandCard=cardId</center></td></tr>
        <tr><td>MR20 </td><td>transferAmount_=transferAmount+1</td><td> handlingFee_=handlingFee=-1</td></tr>
   </table>

   
