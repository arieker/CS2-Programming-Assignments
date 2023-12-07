## Greedy Children (Greedy Algorithm Design)

Objective: Students will apply concepts of greedy algorithm design.

Assignment Description: It is Halloween, and you are handing out pieces of candy to the greedy
children in your neighborhood. Every so often your doorbell rings and outside there are 𝑛
children. You always grab 𝑚 pieces of candy (𝑚 > 𝑛) to hand out. Something interesting about
this Halloween Season that was recommended by a group of concerned parents was to inform
neighbors how greedy their child was and making sure they get the candy they deserve. This
would allow the person at the door to properly hand out candy to the trick or treaters. The idea
was to prevent children from being upset on the pieces of candy each received (along with
egging your house). Some children are picky than others. This can be measured by a “greedy”
factor 𝑔𝑖, 0 ≤ 𝑖 ≤ 𝑛. The greedy factor is displayed on the children’s costume. Each piece of
candy has a sweetness factor represented as 𝑠𝑗, 0 ≤ 𝑗 ≤ 𝑚. As the owner of the house in the
neighborhood, it is your job to maximize the number of greedy children that will get a proper
piece of candy where 𝑔𝑖 ≤ 𝑠𝑗 without throwing some sort of meltdown. Your goal is to come up
with a greedy algorithm that finds the optimal solution along with an efficient implementation
that runs in 𝑂(𝑚𝑙𝑔𝑚) time.