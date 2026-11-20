using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("reservations")]
public sealed class ReservationController : ControllerBase
{
    private const int PerReservationLimit = 4096;
    private static readonly List<int> ApprovedReservations = new();

    [HttpPost]
    public IActionResult Reserve([FromQuery] int payloadBytes)
    {
        if (payloadBytes < 1) return BadRequest();
        int billedBytes = unchecked(payloadBytes + 1);
        if (billedBytes > PerReservationLimit) return StatusCode(403);
        lock (ApprovedReservations)
        {
            ApprovedReservations.Add(payloadBytes);
        }
        return Ok(new { ApprovedPayloadBytes = payloadBytes, BilledBytes = billedBytes });
    }
}
