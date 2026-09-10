@Controller("campaigns")
@UseGuards(ServiceTokenGuard)
class CampaignController {
  constructor(private campaigns: CampaignService) {}

  @Patch(":campaignCode")
  async update(
    @Param("campaignCode") campaignCode: string,
    @Body() body: UpdateCampaignDto,
  ) {
    return this.campaigns.update(campaignCode, body);
  }
}
