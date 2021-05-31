# Logisim-Circuit-Tester

# Background

Logisim[^1] saves circuits as .circ xml file. 
Most of circuit has Input and outpt as *Pin* component.
There are also *Constant* component which accept hex/binary number as input. 
But *Pin* component doesnot accept number as input.

What if for a given circuit we change our intended input/output *Pin* component to *Constant* component and assign number to that constant? It works

So, We change input *Pin* component to *Constant* component and set input value, then load logisim with modified circuit. Logisim will comput output value from input *Constant* values.(No need to manually input value , hard work gone)

If output *Pin* component is changed accordingly , Then output value can be verified by monitoring color of wire.

![Output matches with assigned number](Attachment/correct-result.jpg)

![Output does not match with assigned number](Attachment/incorrect-result.jpg)

# Tasklist

[^1]: logisim-generic-2.7.1.jar is attached from [latest release](https://sourceforge.net/projects/circuit/files/2.7.x/2.7.1/)