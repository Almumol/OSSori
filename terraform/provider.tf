terraform {
  backend "s3" {
    bucket = "ossori"
    key    = "terraform/terraform.tfstate"
    region = "ap-northeast-2"
    dynamodb_table = "tf-locks"
  }
}

resource "aws_key_pair" "terraform_key" {
  key_name = "terraform_key"
  public_key = file("~/.ssh/web_admin.pub")
}

resource "aws_key_pair" "terraform_key_prod" {
  key_name = "terraform_key_prod"
  public_key = file(pathexpand("~/.ssh/ossori_ec2_prod.pub"))
}

resource "aws_security_group" "terraform_ssh_group" {
  name        = "terraform_ssh_group"
  description = "Allow SSH, HTTP, and HTTPS inbound traffic"

  ingress {
    description = "SSH"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "HTTP"
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "HTTPS"
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1" # All protocols
    cidr_blocks = ["0.0.0.0/0"]
  }
}

data "aws_security_group" "default" {
  name = "default"
}

resource "aws_instance" "terraform_dev_instance" {
  ami = "ami-0d5bb3742db8fc264" # Ubuntu Server 24.04 LTS (HVM), SSD Volume Type
  instance_type = "t2.micro"
  key_name = aws_key_pair.terraform_key.key_name
  vpc_security_group_ids = [
    aws_security_group.terraform_ssh_group.id,
  ]
  
  tags = {
     "Name" = "dev"
  }

  root_block_device {
    volume_size = 30
    volume_type = "gp3"
  }
}

resource "aws_security_group" "terraform_ssh_group_prod" {
  name        = "terraform_ssh_group_prod"
  description = "Allow SSH, HTTP, and HTTPS inbound traffic (prod)"

  ingress {
	  description = "SSH"
	  from_port = 22
	  to_port = 22
	  protocol = "tcp"
	  cidr_blocks = []
  }

  ingress {
	  description = "HTTP"
	  from_port = 80
	  to_port = 80
	  protocol = "tcp"
	  cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
	  description = "HTTPS"
	  from_port = 443
	  to_port = 443
	  protocol = "tcp"
	  cidr_blocks = ["0.0.0.0/0"]
  }

  egress  {
	  from_port = 0
	  to_port = 0
	  protocol = "-1"
	  cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "terraform_prod_instance" {
  ami 			= "ami-0d5bb3742db8fc264" # Ubuntu Server 24.04 LTS (HVM), SSD Volume Type
  instance_type = "t2.micro"
  key_name      = aws_key_pair.terraform_key_prod.key_name
  vpc_security_group_ids = [aws_security_group.terraform_ssh_group_prod.id]

  tags = { Name = "prod" }

  root_block_device {
    volume_size = 30
    volume_type = "gp3"
  }
}

resource "aws_s3_bucket" "ossori" {
  bucket = "ossori"
}

resource "aws_dynamodb_table" "tf_locks" {
  name         = "tf-locks"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "LockID"

  attribute {
    name = "LockID"
    type = "S"
  }
}

