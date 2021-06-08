$compress = @{
Path= "p:\tools\myAvatarInterface\*"
CompressionLevel = "Fastest"
DestinationPath = "H:\AvatarMigrationProject"
}
Compress-Archive @compress