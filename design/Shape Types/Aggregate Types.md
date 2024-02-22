| Smithy | .NET |
| ---- | ---- |
| list | List<T> |
| map | Dictionary |
| structure | struct or class<br><br>Need to do concrete performance analysis to determine which is more suitable - perhaps we can choose this dynamically if the intention is for these to be for data only, and without the expectation of needing inheritance? |
| union | We can take a similar approach to document here, however this will vary based on user inputs so we will be building the record class dynamically of having a static definition. |