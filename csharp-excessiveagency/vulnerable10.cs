using Amazon.S3;
using Amazon.S3.Model;
using Microsoft.Extensions.AI;
using System.ComponentModel;

namespace AgentTools;

public class StorageCleanup
{
    private readonly IAmazonS3 _s3;
    private readonly string _bucketName;

    public StorageCleanup(IAmazonS3 s3, string bucketName)
    {
        _s3 = s3;
        _bucketName = bucketName;
    }

    public AIFunction GetDeleteTool()
    {
        return AIFunctionFactory.Create(
            ([Description("S3 object key to delete")] string key) =>
            {
                var request = new DeleteObjectRequest
                {
                    BucketName = _bucketName,
                    Key = key
                };
                return _s3.DeleteObjectAsync(request);
            },
            "delete_s3_object",
            "Delete an object from S3 storage"
        );
    }
}
