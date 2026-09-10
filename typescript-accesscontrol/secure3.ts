class CampaignClient {
  constructor(
    private http: HttpClient,
    private authClient: AuthClient,
  ) {}

  async updateCampaign(campaignCode: string, body: UpdateCampaignDto) {
    const token = await this.authClient.getToken();
    return this.http.patch(`/campaigns/${campaignCode}`, body, {
      headers: { Authorization: `Bearer ${token}` },
    });
  }
}

@Controller("campaigns")
@UseGuards(UserAuthGuard)
class CampaignController {
  constructor(
    private campaigns: CampaignService,
    private campaignClient: CampaignClient,
  ) {}

  @Patch(":campaignCode")
  async update(
    @CurrentUser() user: AuthenticatedUser,
    @Param("campaignCode") campaignCode: string,
    @Body() body: UpdateCampaignDto,
  ) {
    await this.campaigns.assertOwnedByTenant(campaignCode, user.tenantId);
    return this.campaignClient.updateCampaign(campaignCode, body);
  }
}
